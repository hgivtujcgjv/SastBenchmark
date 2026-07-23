module.exports = (req, res) => {
    res.sendFile('/var/data/' + req.params.name);
};
