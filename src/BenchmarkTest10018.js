function merge(target, source) {
    for (const key in source) {
        if (key === '__proto__' || key === 'constructor') continue;
        target[key] = source[key];
    }
    return target;
}
merge({}, JSON.parse(new URLSearchParams(location.search).get('opts')));
