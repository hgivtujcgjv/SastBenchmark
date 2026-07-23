const _ = require('lodash');
module.exports = (req, res) => {
    const config = {};
    _.merge(config, req.body);
    res.json(config);
};
