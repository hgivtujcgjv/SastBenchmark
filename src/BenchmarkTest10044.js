const pug = require('pug');
module.exports = (req, res) => {
    const fn = pug.compile(req.body.view);
    res.send(fn());
};
