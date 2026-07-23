# SastBenchmark — JavaScript / Node.js

Набор тест-кейсов для проверки полноты и точности статических анализаторов.

> **Внимание.** Код содержит намеренные уязвимости и предназначен только для тестирования
> анализаторов в изолированной среде. Не разворачивайте его и не используйте фрагменты в
> рабочих проектах.

## Состав

- **Кейсов:** 49
- **Идентификаторы:** BenchmarkTest10001 – BenchmarkTest10049
- **Категорий:** 11

| Категория | CWE | Окружение |
|---|---|---|
| `domxss` | CWE-79 | browser (DOM) |
| `domclobber` | CWE-79 | browser (DOM) |
| `protopollute` | CWE-1321 | browser |
| `postmessage` | CWE-345 | browser |
| `protopollute-node` | CWE-1321 | Node.js |
| `cmdi` | CWE-78 | Node.js |
| `nosqli` | CWE-943 | Node.js |
| `codeinj` | CWE-94 | Node.js |
| `pathtraver` | CWE-22 | Node.js |
| `ssti` | CWE-1336 | Node.js |
| `ssrf` | CWE-918 | Node.js |

Client-side кейсы обращаются к браузерным глобалам (`location`, `document`, `window`,
`localStorage`, `postMessage`) как к источникам недоверенных данных. Node.js кейсы — обработчики
запросов (`req` как источник) с `require`. Каждая категория содержит уязвимые формы и
безопасные контроли с настоящей митигацией.

Синтаксис всех файлов проверен `node --check`. Зависимости в `package.json` не обязательны для
статического анализа. Разметка **не включена в эту ветку** — прогон слепой. Ключи в ветке
[`results`](../../tree/results).

## Структура

`src/BenchmarkTestNNNNN.js`, `package.json`
