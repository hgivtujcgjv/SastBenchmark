const fs = require('fs');
const path = require('path');
module.exports = (req, res) => {
    const file = path.join('/var/data', req.query.name);
    res.send(fs.readFileSync(file, 'utf8'));
};
