module.exports = (req, res) => {
    const fn = new Function('data', req.body.code);
    res.json({ out: fn(req.body.data) });
};
