const query = location.search.slice(1).split('&').reduce((acc, pair) => {
    const [k, v] = pair.split('=');
    acc[decodeURIComponent(k)] = decodeURIComponent(v || '');
    return acc;
}, {});
Object.assign(Object.prototype, query.__proto__ || {});
