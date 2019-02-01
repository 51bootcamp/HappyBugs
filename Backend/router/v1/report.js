const express = require('express');
const router = express.Router();
const reportCtrl = require('./report.ctrl');

module.exports = () => {
  router.use((req, res, next) => {
    next();
  });

  router.all('/', (req, res) => {
    res.status(404).json({statusCode: 1001});
  });

router.post('/create', reportCtrl.createReport);
router.get('/list', reportCtrl.showReportList);
router.get('/find', reportCtrl.findReport);
router.delete('/delete', reportCtrl.deleteReport);
router.put('/update', reportCtrl.editReport);

return router;
};
