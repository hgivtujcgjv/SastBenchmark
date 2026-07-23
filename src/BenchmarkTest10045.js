const ejs = require('ejs');
const TEMPLATE = 'Hello <%= name %>';
module.exports = (req, res) => {
    res.send(ejs.render(TEMPLATE, { name: String(req.query.name) }));
};
