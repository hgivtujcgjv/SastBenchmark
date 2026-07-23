const fs = require('fs');
const path = require('path');
module.exports = (req, res) => {
    const name = path.basename(req.query.name);
    res.send(fs.readFileSync(path.join('/var/data', name), 'utf8'));
};
