const axios = require('axios');
const ALLOWED = new Set(['api.internal', 'cdn.internal']);
module.exports = async (req, res) => {
    const u = new URL(req.query.url);
    if (!ALLOWED.has(u.hostname)) return res.status(400).send('rejected');
    res.json((await axios.get(u.toString())).data);
};
