# spring-bench-v2 — какая форма в каком кейсе

Разметка вынесена сюда, в исходниках кейсов её нет.


## `cmdi-entry` (CWE-78)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06001 | да | @RestController + @GetMapping — базовая форма | КОНТРОЛЬ |
| BenchmarkTest06002 | да | @Controller + @RequestMapping(POST) | под вопросом |
| BenchmarkTest06003 | да | @RequestBody в record — источник внутри объекта | под вопросом |
| BenchmarkTest06004 | да | @PathVariable — переменная пути | под вопросом |
| BenchmarkTest06005 | да | функциональный роутинг WebMvc.fn — в v1 дал 0/4 | СЛЕПАЯ(v1 0/3) |
| BenchmarkTest06006 | да | HandlerInterceptor — вход до контроллера | под вопросом |
| BenchmarkTest06007 | да | OncePerRequestFilter — фильтр сервлета | под вопросом |
| BenchmarkTest06008 | да | @ExceptionHandler как точка входа, источник — request.getParameter (явный) | под вопросом |
| BenchmarkTest06009 | да | сервлет внутри Spring Boot — контрольная форма, в java-bench работает | под вопросом |
| BenchmarkTest06010 | да | CommandLineRunner — не веб-вход, аргументы запуска | под вопросом |
| BenchmarkTest06011 | да | @RequestHeader | под вопросом |
| BenchmarkTest06012 | да | @RequestPart — имя загруженного файла | под вопросом |
| BenchmarkTest06013 | нет | ProcessBuilder с массивом аргументов — интерпретатор не запускается | под вопросом |
| BenchmarkTest06014 | нет | allowlist через Set.contains | под вопросом |
| BenchmarkTest06015 | нет | тот же интерцептор, но безопасный приёмник | под вопросом |
| BenchmarkTest06016 | нет | тот же роутер, безопасный приёмник | под вопросом |

## `cmdi-source` (CWE-78)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06021 | да | @RequestParam — базовый | КОНТРОЛЬ |
| BenchmarkTest06022 | да | @RequestHeader | под вопросом |
| BenchmarkTest06023 | да | @CookieValue | под вопросом |
| BenchmarkTest06024 | да | getQueryString внутри контроллера — КЛЮЧЕВОЙ кейс, в OWASP-Java recall 9.1% | СЛЕПАЯ(OWASP 9.1%) |
| BenchmarkTest06025 | да | getParameter через сервлетный API — контроль, в OWASP-Java 94.2% | КОНТРОЛЬ(контраст к 6024) |
| BenchmarkTest06026 | да | @RequestParam Map — источник через коллекцию | под вопросом |
| BenchmarkTest06027 | да | MultiValueMap | под вопросом |
| BenchmarkTest06028 | да | getHeader сервлетный | под вопросом |
| BenchmarkTest06029 | да | URI запроса как источник | под вопросом |
| BenchmarkTest06030 | да | имя пользователя из Principal — данные из токена | под вопросом |
| BenchmarkTest06031 | да | весь HttpHeaders | под вопросом |
| BenchmarkTest06032 | да | тело запроса через getInputStream | под вопросом |
| BenchmarkTest06033 | нет | источник не используется — константа | под вопросом |
| BenchmarkTest06034 | нет | allowlist | под вопросом |
| BenchmarkTest06035 | нет | getQueryString + строгая regex-валидация | под вопросом |
| BenchmarkTest06036 | нет | санитизация — удаление всех спецсимволов | под вопросом |

## `redirect2` (CWE-601)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06101 | да | строковый префикс "redirect:" — в v1 пропущен | СЛЕПАЯ(v1 0/3) |
| BenchmarkTest06102 | да | RedirectView возвращается из метода | СЛЕПАЯ |
| BenchmarkTest06103 | да | sendRedirect внутри контроллера — КОНТРОЛЬ, в сервлетах работает | КОНТРОЛЬ |
| BenchmarkTest06104 | да | ResponseEntity.location возвращается из метода | СЛЕПАЯ |
| BenchmarkTest06105 | да | ModelAndView возвращается из метода | СЛЕПАЯ |
| BenchmarkTest06106 | нет | allowlist путей | под вопросом |
| BenchmarkTest06107 | нет | относительный путь + санитизация | под вопросом |
| BenchmarkTest06108 | нет | отклонение абсолютных URI | под вопросом |

## `ssrf2` (CWE-918)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06111 | да | RestTemplate — в v1 пропущен | СЛЕПАЯ(v1 0/3) |
| BenchmarkTest06112 | да | RestClient (Spring 6.1) | СЛЕПАЯ |
| BenchmarkTest06113 | да | URL.openStream — КОНТРОЛЬ, классическая форма | КОНТРОЛЬ |
| BenchmarkTest06114 | да | JDK HttpClient | под вопросом |
| BenchmarkTest06115 | да | RestTemplate.exchange с RequestEntity | СЛЕПАЯ |
| BenchmarkTest06116 | нет | allowlist хостов | под вопросом |
| BenchmarkTest06117 | нет | параметризованный URL — пользовательские данные только в path-переменной | под вопросом |
| BenchmarkTest06118 | нет | строгая валидация сегмента | под вопросом |

## `sqlinj2` (CWE-89)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06121 | да | JdbcTemplate.execute — КОНТРОЛЬ, в v1 найден | КОНТРОЛЬ |
| BenchmarkTest06122 | да | JPQL createQuery — в v1 пропущен | СЛЕПАЯ(v1) |
| BenchmarkTest06123 | да | createNativeQuery | под вопросом |
| BenchmarkTest06124 | да | JdbcTemplate.queryForList | под вопросом |
| BenchmarkTest06125 | да | NamedParameterJdbcTemplate с конкатенацией вместо параметра | под вопросом |
| BenchmarkTest06126 | да | queryForObject | под вопросом |
| BenchmarkTest06127 | нет | параметризованный запрос | под вопросом |
| BenchmarkTest06128 | нет | JPQL с именованным параметром | под вопросом |
| BenchmarkTest06129 | нет | allowlist имён колонок — единственный корректный способ подставить идентификатор | под вопросом |

## `pathtraver2` (CWE-22)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06131 | да | Files.readAllBytes + Path.of — КОНТРОЛЬ, классическая форма | КОНТРОЛЬ |
| BenchmarkTest06132 | да | FileSystemResource с конкатенацией | под вопросом |
| BenchmarkTest06133 | да | ResourceLoader.getResource | под вопросом |
| BenchmarkTest06134 | да | InputStreamResource поверх FileInputStream | под вопросом |
| BenchmarkTest06135 | да | ServletContext.getRealPath | под вопросом |
| BenchmarkTest06136 | нет | канонизация + проверка префикса | под вопросом |
| BenchmarkTest06137 | нет | allowlist имён файлов | под вопросом |
| BenchmarkTest06138 | нет | regex-валидация имени файла | под вопросом |

## `spel2` (CWE-917)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06141 | да | SpelExpressionParser напрямую | СЛЕПАЯ |
| BenchmarkTest06142 | да | SpEL со StandardEvaluationContext | СЛЕПАЯ |
| BenchmarkTest06143 | да | SpEL в шаблонном режиме #{...} | СЛЕПАЯ |
| BenchmarkTest06144 | да | конкатенация в выражение | СЛЕПАЯ |
| BenchmarkTest06145 | нет | SimpleEvaluationContext + выражение-константа | под вопросом |
| BenchmarkTest06146 | нет | allowlist выражений | под вопросом |

## `templateinj2` (CWE-1336)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06151 | да | Thymeleaf process с шаблоном из параметра — в v1 пропущен | СЛЕПАЯ |
| BenchmarkTest06152 | да | имя view целиком из параметра | СЛЕПАЯ |
| BenchmarkTest06153 | да | конкатенация в inline-выражение Thymeleaf | СЛЕПАЯ |
| BenchmarkTest06154 | да | fragment-выражение из параметра | СЛЕПАЯ |
| BenchmarkTest06155 | нет | шаблон фиксирован, данные — через переменную контекста | под вопросом |
| BenchmarkTest06156 | нет | allowlist имён шаблонов | под вопросом |

## `xxe2` (CWE-611)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06161 | да | DocumentBuilderFactory — КОНТРОЛЬ, в v1 найден | КОНТРОЛЬ |
| BenchmarkTest06162 | да | SAXParserFactory | под вопросом |
| BenchmarkTest06163 | да | StAX XMLInputFactory | под вопросом |
| BenchmarkTest06164 | да | TransformerFactory | под вопросом |
| BenchmarkTest06165 | да | JAXB Unmarshaller | под вопросом |
| BenchmarkTest06166 | нет | disallow-doctype-decl + запрет внешних DTD | под вопросом |
| BenchmarkTest06167 | нет | StAX с отключёнными DTD и внешними сущностями | под вопросом |

## `deser2` (CWE-502)

| кейс | уязвим | форма |
|---|---|---|---|
| BenchmarkTest06171 | да | ObjectInputStream — КОНТРОЛЬ, в v1 найден | КОНТРОЛЬ |
| BenchmarkTest06172 | да | XMLDecoder — в v1 распознан как RCE | КОНТРОЛЬ |
| BenchmarkTest06173 | да | SnakeYAML без SafeConstructor | под вопросом |
| BenchmarkTest06174 | да | Jackson с activateDefaultTyping — полиморфная десериализация | под вопросом |
| BenchmarkTest06175 | нет | SnakeYAML c SafeConstructor | под вопросом |
| BenchmarkTest06176 | нет | Jackson в конкретный тип, без default typing | под вопросом |
