module.exports = async (req, res) => {
    const r = await fetch(req.query.target);
    res.send(await r.text());
};
