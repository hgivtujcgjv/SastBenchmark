# js-bench — какой кейс что проверяет

Разметка здесь, в коде подсказок нет.


## `domxss` (CWE-79)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10001 | да | location.hash -> innerHTML |
| BenchmarkTest10002 | да | location.search -> document.write |
| BenchmarkTest10003 | да | window.name -> eval |
| BenchmarkTest10004 | да | document.referrer -> insertAdjacentHTML |
| BenchmarkTest10005 | да | location.hash -> srcdoc attribute |
| BenchmarkTest10006 | да | jQuery .html() -> CONTROL, PT AI видит jQuery DOM |
| BenchmarkTest10007 | да | location.hash -> href (javascript: DOM XSS) |
| BenchmarkTest10008 | да | localStorage -> innerHTML (stored DOM XSS) |
| BenchmarkTest10009 | нет | SAFE: textContent — не парсит HTML |
| BenchmarkTest10010 | нет | SAFE: DOMPurify.sanitize перед innerHTML |

## `domclobber` (CWE-79)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10011 | да | window.config не объявлен -> clobbering <a id=config name=url> |
| BenchmarkTest10012 | да | window.CDN перезаписывается <img name=CDN> |
| BenchmarkTest10013 | да | window.loaded clobbering — form/img id |
| BenchmarkTest10014 | нет | SAFE: явный объект + проверка типа и префикса |

## `protopollute` (CWE-1321)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10015 | да | рекурсивный merge без защиты __proto__ |
| BenchmarkTest10016 | да | setByPath с управляемым path (__proto__.x) |
| BenchmarkTest10017 | да | разбор query в объект + Object.assign в prototype |
| BenchmarkTest10018 | нет | SAFE: merge с фильтром __proto__/constructor |

## `postmessage` (CWE-345)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10019 | да | обработчик message без проверки origin -> innerHTML |
| BenchmarkTest10020 | да | message.data -> eval, без origin |
| BenchmarkTest10021 | да | message -> location.href, без origin |
| BenchmarkTest10022 | нет | SAFE: проверка origin + textContent |

## `protopollute-node` (CWE-1321)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10023 | да | lodash.merge(config, req.body) — CVE-класс |
| BenchmarkTest10024 | да | самописный deepAssign от req.body |
| BenchmarkTest10025 | нет | SAFE: allowlist ключей + null-prototype |

## `cmdi` (CWE-78)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10026 | да | child_process.exec с конкатенацией |
| BenchmarkTest10027 | да | execSync с template literal |
| BenchmarkTest10028 | да | spawn sh -c с конкатенацией |
| BenchmarkTest10029 | нет | SAFE: execFile с массивом аргументов |

## `nosqli` (CWE-943)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10030 | да | req.body-объект прямо в findOne ({$ne:null}) |
| BenchmarkTest10031 | да | $where с конкатенацией — JS-инъекция |
| BenchmarkTest10032 | да | req.query.role как объект-оператор в find |
| BenchmarkTest10033 | нет | SAFE: приведение к String снимает операторы |

## `codeinj` (CWE-94)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10034 | да | eval(req.query.expr) |
| BenchmarkTest10035 | да | new Function из тела запроса |
| BenchmarkTest10036 | да | vm.runInNewContext — песочница обходится |
| BenchmarkTest10037 | нет | SAFE: allowlist операций вместо eval |

## `pathtraver` (CWE-22)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10038 | да | path.join(base, req.query) — ../ проходит |
| BenchmarkTest10039 | да | createReadStream от параметра напрямую |
| BenchmarkTest10040 | да | res.sendFile с конкатенацией |
| BenchmarkTest10041 | нет | SAFE: path.basename срезает путь |

## `ssti` (CWE-1336)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10042 | да | handlebars.compile от тела запроса |
| BenchmarkTest10043 | да | ejs.render с пользовательским шаблоном |
| BenchmarkTest10044 | да | pug.compile от пользователя |
| BenchmarkTest10045 | нет | SAFE: фиксированный шаблон, данные через контекст |

## `ssrf` (CWE-918)

| кейс | уязвим | форма |
|---|---|---|
| BenchmarkTest10046 | да | http.get(req.query.url) |
| BenchmarkTest10047 | да | axios.get(req.body.callback) |
| BenchmarkTest10048 | да | fetch(req.query.target) |
| BenchmarkTest10049 | нет | SAFE: allowlist хостов |
