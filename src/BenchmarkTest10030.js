module.exports = async (req, res) => {
    const user = await db.collection('users').findOne({
        username: req.body.username,
        password: req.body.password
    });
    res.json(user);
};
