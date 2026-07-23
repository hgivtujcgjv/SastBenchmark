module.exports = async (req, res) => {
    const q = { status: 'active' };
    q.role = req.query.role;
    res.json(await db.collection('users').find(q).toArray());
};
