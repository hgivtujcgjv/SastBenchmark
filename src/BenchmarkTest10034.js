module.exports = (req, res) => {
    const result = eval(req.query.expr);
    res.json({ result });
};
