function merge(target, source) {
    for (const key in source) {
        if (typeof source[key] === 'object') {
            target[key] = merge(target[key] || {}, source[key]);
        } else {
            target[key] = source[key];
        }
    }
    return target;
}
const params = JSON.parse(new URLSearchParams(location.search).get('opts'));
merge({}, params);
