module.exports = async (req, res) => {
    const username = String(req.body.username);
    const user = await db.collection('users').findOne({ username });
    res.json(user);
};
