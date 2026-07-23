module.exports = (req, res) => {
    const allowed = { add: (a, b) => a + b, mul: (a, b) => a * b };
    const op = allowed[req.query.op];
    res.json({ result: op ? op(1, 2) : null });
};
