const { spawn } = require('child_process');
module.exports = (req, res) => {
    const p = spawn('sh', ['-c', 'convert ' + req.query.file + ' out.png']);
    p.on('close', () => res.send('ok'));
};
