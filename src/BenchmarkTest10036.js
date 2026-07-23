const vm = require('vm');
module.exports = (req, res) => {
    const sandbox = {};
    vm.runInNewContext(req.body.script, sandbox);
    res.json(sandbox);
};
