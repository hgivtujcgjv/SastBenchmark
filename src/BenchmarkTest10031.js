module.exports = async (req, res) => {
    const rows = await db.collection('items').find({
        $where: `this.owner == '${req.query.owner}'`
    }).toArray();
    res.json(rows);
};
