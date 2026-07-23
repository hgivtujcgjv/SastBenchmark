const http = require('http');
module.exports = (req, res) => {
    http.get(req.query.url, (r) => r.pipe(res));
};
