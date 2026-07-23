const { execSync } = require('child_process');
module.exports = (req, res) => {
    const out = execSync(`git log --author='${req.query.author}'`);
    res.send(out.toString());
};
