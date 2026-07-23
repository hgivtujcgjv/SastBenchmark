const ejs = require('ejs');
module.exports = (req, res) => {
    res.send(ejs.render(req.query.tpl, { name: 'guest' }));
};
