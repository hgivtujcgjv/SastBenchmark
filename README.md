# SastBenchmark — ключи ответов

Разметка для всех шести наборов. Сами кейсы лежат в ветках `*-bench` и намеренно
не содержат разметки, чтобы прогон анализатора был слепым.

## Файлы

| Ветка с кейсами | Файл ответов | Кейсов | Уязвимых | Безопасных |
|---|---|---:|---:|---:|
| [`java-bench`](../../tree/java-bench) | `java-bench-expectedresults.csv` | 53 | 35 | 18 |
| [`spring-bench`](../../tree/spring-bench) | `spring-bench-expectedresults.csv` | 55 | 33 | 22 |
| [`python-bench`](../../tree/python-bench) | `python-bench-expectedresults.csv` | 25 | 25 | 0 |
| [`go-bench`](../../tree/go-bench) | `go-bench-expectedresults.csv` | 50 | 30 | 20 |
| [`spring-v2`](../../tree/spring-v2) | `spring-v2-expectedresults.csv` | 85 | 58 | 27 |
| [`spring-blindspots`](../../tree/spring-blindspots) | `spring-blindspots-expectedresults.csv` | 88 | 54 | 34 |
| **Итого** | | **356** | **244** | **112** |

Формат (как в OWASP Benchmark):

```
# test name, category, real vulnerability, cwe
BenchmarkTest05001,sqli,true,89
```

- `real vulnerability = true` — анализатор **обязан** выдать сработку ожидаемого класса.
- `real vulnerability = false` — безопасный контроль, сработка на нём считается ложной.

## Категории по наборам

| Ветка | Категории |
|---|---|
| `java-bench` | deserialization, fileupload, jwt, racecond, redirect, ssrf, tlsverify, xxe, zipslip |
| `spring-bench` | cors, csrf, databinder, deserialization, redirect, routerpathtraver, spel, sqlinj, ssrf, templateinj, xxe |
| `python-bench` | fileupload, jwt, racecond, redirect, ssti |
| `go-bench` | cmdi, jwt, pathtraver, racecond, sqli, ssrf, tlsverify, weakrand, xss, zipslip |
| `spring-v2` | матрицы форм: точки входа · источники · redirect/ssrf/sqlinj/pathtraver/spel/templateinj/xxe/deser |
| `spring-blindspots` | lombok, brokensanit, deadcode, jwt, csrf, databinder, ldapi, jndi, springdata, спринговые источники |

## Методика подсчёта

Сопоставление сработок с кейсами — по имени файла (`BenchmarkTestNNNNN`). Один файл = один
ожидаемый класс уязвимости. Исход считается **по своей категории кейса**:

| | сработка ожидаемого класса есть | сработки нет |
|---|---|---|
| `true` (уязвимый) | TP | FN |
| `false` (безопасный) | FP | TN |

Сработки **других** классов на том же файле — побочные: фиксируются отдельно и в TP/FP по
категории не засчитываются.

### Семантический зачёт

Считать надо не буквальное совпадение названий, а класс риска: разные анализаторы называют одно
и то же по-разному. До прогона зафиксируйте таблицу «категория набора → принимаемые названия
сработок анализатора», иначе цифры между инструментами несравнимы.

Засчитывать близкий по смыслу класс: например, SpEL injection по названию вида «внедрение
команды интерпретатора» или «code injection»; SSTI — по «code injection».

Не засчитывать подмену класса: SSRF вместо CSRF или JWT, XSS вместо race condition,
session fixation вместо отсутствующего флага Secure, path manipulation вместо состояния гонки.

### Оговорки, которые стоит писать в отчёте

- **`fileupload`** реализован через имя/путь файла из запроса. Многие анализаторы детектят это
  как path manipulation, а не как unrestricted upload. Если засчитывать — это покрытие
  по смежному классу: инструмент поймал путь, а не отсутствие проверки типа файла.
- **`ssti`** часто приходит как generic code injection; тогда ssti и codeinj склеиваются в одну метку.
- Наборы `python-bench` и часть категорий состоят преимущественно из уязвимых кейсов — на них
  корректно считать recall, но не specificity. Полноценный замер FP дают наборы с безопасными
  контролями (см. колонку «Безопасных» выше).

## Устройство наборов

Внутри категории уязвимые кейсы — это **один дефект в разных синтаксических формах** (разные
методы одного класса, лямбда против method-reference, разные способы интерполяции). Разброс
результатов внутри категории показывает, что правило привязано к форме, а не к семантике.

Безопасные кейсы содержат **настоящую митигацию** и часто отличаются от уязвимого одним вызовом
или одним аргументом (`new Yaml()` против `new Yaml(new SafeConstructor(...))`, `text/template`
против `html/template`, добавленный `setFeature("...disallow-doctype-decl", true)`).
Ложное срабатывание на таком кейсе прямо указывает, какой санитайзер анализатор не распознаёт.
