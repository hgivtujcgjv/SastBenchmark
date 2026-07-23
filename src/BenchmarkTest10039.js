const fs = require('fs');
module.exports = (req, res) => {
    fs.createReadStream(req.query.path).pipe(res);
};
