const axios = require('axios');
module.exports = async (req, res) => {
    const r = await axios.get(req.body.callback);
    res.json(r.data);
};
