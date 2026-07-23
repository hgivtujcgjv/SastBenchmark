module.exports = (req, res) => {
    const settings = Object.create(null);
    for (const k of ['name', 'theme', 'lang']) {
        if (typeof req.body[k] === 'string') settings[k] = req.body[k];
    }
    res.json(settings);
};
