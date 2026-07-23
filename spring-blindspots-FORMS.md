# spring-blindspots — объединение block07 + block08 + block09

88 кейсов. Разметка здесь, в коде подсказок нет.

## Происхождение
| блок | категории | суть |
|---|---|---|
| 07 | lombok, brokensanit, deadcode | слабости анализа: геттеры Lombok, сломанные санитайзеры, мёртвый код |
| 08 | jwt, csrf, databinder, ldapi, jndi, springdata | серверные Spring-классы, которых нет правил |
| 09 | cmdi-springsrc | спринговые источники (приёмник exec фиксирован) |

---



## `lombok` (CWE-78)

| кейс | уязвим | что проверяет |
|---|---|---|
| BenchmarkTest07001 | да | @Data — getHost() генерируется Lombok, в исходнике его нет |
| BenchmarkTest07002 | да | @Getter/@Setter |
| BenchmarkTest07003 | да | @Value — immutable, поля final, геттеры генерируются |
| BenchmarkTest07004 | да | @Builder + @Getter |
| BenchmarkTest07005 | да | @Accessors(fluent) — геттер называется host(), не getHost() |
| BenchmarkTest07006 | да | цепочка сгенерированных геттеров dto.getInner().getHost() |
| BenchmarkTest07007 | да | @AllArgsConstructor + @Getter |
| BenchmarkTest07008 | да | @SneakyThrows прячет checked-исключение — проверяемый поток не должен теряться |
| BenchmarkTest07009 | да | @Builder-цепочка .host(host).build() — источник через сгенерированный сеттер билдера |
| BenchmarkTest07010 | да | КОНТРОЛЬ: тот же DTO с РУЧНЫМ геттером, без Lombok — должен находиться |
| BenchmarkTest07011 | нет | БЕЗОПАСНЫЙ: Lombok-геттер + allowlist |
| BenchmarkTest07012 | нет | БЕЗОПАСНЫЙ: Lombok-геттер + ProcessBuilder с массивом |

## `brokensanit` (CWE-78)

| кейс | уязвим | что проверяет |
|---|---|---|
| BenchmarkTest07021 | да | blacklist вместо allowlist — «; rm -rf» проходит |
| BenchmarkTest07022 | да | regex без якорей ^$ — совпадает с подстрокой, не проверяет всю строку |
| BenchmarkTest07023 | да | replace("../","") обходится через ....// — рекурсии нет |
| BenchmarkTest07024 | да | normalize() без последующей проверки startsWith(base) — не мешает выходу вверх |
| BenchmarkTest07025 | да | валидация выполнена, но результат проигнорирован |
| BenchmarkTest07026 | да | проверка внутри недостижимой для реальных данных ветки |
| BenchmarkTest07027 | да | точка в классе символов разрешена, «..» проходит валидацию |
| BenchmarkTest07028 | да | HTML-энкодер применён к SQL-приёмнику — не тот санитайзер, кавычка не экранирована |
| BenchmarkTest07029 | да | trim + toLowerCase — косметика, спецсимволы остаются |
| BenchmarkTest07030 | да | проверка только длины — «;id» короче 64 |
| BenchmarkTest07031 | да | проверка ДО URL-декодирования — %2e%2e проходит и раскрывается в .. |
| BenchmarkTest07032 | да | удаление схемы не мешает //evil.com (protocol-relative) |

## `deadcode` (CWE-78)

| кейс | уязвим | что проверяет |
|---|---|---|
| BenchmarkTest07041 | нет | if (false) — приёмник в мёртвой ветке |
| BenchmarkTest07042 | нет | код после безусловного return |
| BenchmarkTest07043 | нет | приватный метод с приёмником не вызывается ниоткуда |
| BenchmarkTest07044 | нет | гард по константе static final boolean DEBUG = false |
| BenchmarkTest07045 | нет | условие по константе 1 == 2 — недостижимо |
| BenchmarkTest07046 | нет | приёмник после безусловного throw |
| BenchmarkTest07047 | нет | гард с всегда-ложной конъюнкцией |
| BenchmarkTest07048 | нет | переменная с источником перезаписана константой до приёмника |
| BenchmarkTest07049 | да | КОНТРОЛЬ: приёмник достижим — молчание здесь означало бы, что anti-FP слишком агрессивен |
| BenchmarkTest07050 | да | КОНТРОЛЬ: достижим при p != null — тоже уязвим |
---



## `jwt` (CWE-347)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest08001 | да | ручной разбор payload без проверки подписи — доверие claim role |
| BenchmarkTest08002 | да | alg:none — токен без подписи принят как валидный |
| BenchmarkTest08003 | да | жёстко зашитый секрет подписи JWT |
| BenchmarkTest08004 | да | claim из непроверенного JWT доходит до чтения файла (path traversal через JWT) |
| BenchmarkTest08005 | да | подпись выпускаемого токена — константа |
| BenchmarkTest08006 | нет | БЕЗОПАСНО: секрет из конфигурации + проверка подписи HMAC |
| BenchmarkTest08007 | нет | БЕЗОПАСНО: HMAC-проверка подписи с constant-time сравнением, секрет из конфига |

## `csrf` (CWE-352)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest08011 | да | http.csrf().disable() — защита выключена глобально |
| BenchmarkTest08012 | да | csrf(AbstractHttpConfigurer::disable) — method reference |
| BenchmarkTest08013 | да | ignoringRequestMatchers("/**") — исключены все пути |
| BenchmarkTest08014 | нет | БЕЗОПАСНО: CSRF включён по умолчанию, не отключается |
| BenchmarkTest08015 | нет | БЕЗОПАСНО: CSRF-токен в cookie, защита сохранена |

## `databinder` (CWE-915)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest08021 | да | @ModelAttribute на bean с полем admin — привилегия из параметра |
| BenchmarkTest08022 | да | WebDataBinder.bind без setAllowedFields |
| BenchmarkTest08023 | да | @InitBinder с blacklist setDisallowedFields — admin не закрыт |
| BenchmarkTest08024 | нет | БЕЗОПАСНО: @InitBinder setAllowedFields("name") |
| BenchmarkTest08025 | нет | БЕЗОПАСНО: DTO-record только с безопасным полем name |

## `ldapi` (CWE-90)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest08031 | да | конкатенация в LDAP-фильтр (uid= + user) |
| BenchmarkTest08032 | да | два непроверенных параметра в составном фильтре |
| BenchmarkTest08033 | да | конкатенация в DN при lookup |
| BenchmarkTest08034 | нет | БЕЗОПАСНО: параметризованный фильтр (uid={0}) с escaping |
| BenchmarkTest08035 | нет | БЕЗОПАСНО: строгая валидация до подстановки |

## `jndi` (CWE-74)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest08041 | да | InitialContext.lookup(userInput) — удалённая загрузка кода |
| BenchmarkTest08042 | да | DirContext.lookup(userInput) |
| BenchmarkTest08043 | да | lookup управляемого ref с последующим rebind |
| BenchmarkTest08044 | нет | БЕЗОПАСНО: allowlist JNDI-имён |
| BenchmarkTest08045 | нет | БЕЗОПАСНО: константное имя, параметр игнорируется |

## `springdata` (CWE-89)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest08051 | да | JPQL ORDER BY с конкатенацией — sort injection |
| BenchmarkTest08052 | да | Sort.by(userField) — недоверенное имя поля в ORDER BY |
| BenchmarkTest08053 | да | createNativeQuery с конкатенацией |
| BenchmarkTest08054 | да | имя таблицы и значение оба недоверенные |
| BenchmarkTest08055 | да | конкатенация внутри LIKE-шаблона |
| BenchmarkTest08056 | нет | БЕЗОПАСНО: именованный параметр |
| BenchmarkTest08057 | нет | БЕЗОПАСНО: allowlist сортируемых полей |
---


| кейс | уязвим | источник |
|---|---|---|
| BenchmarkTest09001 | да | @RequestParam — КОНТРОЛЬ, обязан находиться |
| BenchmarkTest09002 | да | @PathVariable |
| BenchmarkTest09003 | да | @MatrixVariable — редкий вход |
| BenchmarkTest09004 | да | @RequestAttribute — значение, положенное фильтром |
| BenchmarkTest09005 | да | @SessionAttribute |
| BenchmarkTest09006 | да | @CookieValue |
| BenchmarkTest09007 | да | @RequestParam Map |
| BenchmarkTest09008 | да | MultiValueMap |
| BenchmarkTest09009 | да | @RequestParam Optional |
| BenchmarkTest09010 | да | @RequestParam List |
| BenchmarkTest09011 | да | @RequestHeader Map — все заголовки |
| BenchmarkTest09012 | да | WebRequest.getParameter — абстракция поверх сервлета |
| BenchmarkTest09013 | да | разбор URL через UriComponentsBuilder — данные из query |
| BenchmarkTest09014 | да | getPathInfo — часть пути после сервлета |
| BenchmarkTest09015 | да | getRequestURL |
| BenchmarkTest09016 | да | getParameterMap — массив значений |
| BenchmarkTest09017 | да | имя загруженного файла |
| BenchmarkTest09018 | да | имя из Principal — данные из токена/сессии |
| BenchmarkTest09019 | нет | БЕЗОПАСНО: источник не используется, константа |
| BenchmarkTest09020 | нет | БЕЗОПАСНО: allowlist перед приёмником |
