function deepAssign(target, source) {
    for (const k in source) {
        if (typeof source[k] === 'object' && source[k]) {
            target[k] = target[k] || {};
            deepAssign(target[k], source[k]);
        } else target[k] = source[k];
    }
}
module.exports = (req, res) => {
    const settings = {};
    deepAssign(settings, req.body);
    res.json(settings);
};
