exports.helloGET = function helloGET (req, res) {
    res.send('Current Date is: ' + (new Date()).toLocaleDateString());
};