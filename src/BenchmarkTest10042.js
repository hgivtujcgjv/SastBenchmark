const handlebars = require('handlebars');
module.exports = (req, res) => {
    const tpl = handlebars.compile(req.body.template);
    res.send(tpl({ user: req.user }));
};
