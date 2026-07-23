function setByPath(obj, path, value) {
    const keys = path.split('.');
    let cur = obj;
    for (let i = 0; i < keys.length - 1; i++) {
        cur = cur[keys[i]] = cur[keys[i]] || {};
    }
    cur[keys[keys.length - 1]] = value;
}
const p = new URLSearchParams(location.search);
setByPath({}, p.get('path'), p.get('val'));
