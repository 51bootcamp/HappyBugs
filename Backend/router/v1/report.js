const express = require('express');
const router = express.Router();
const reportCtrl = require('./report.ctrl');

router.use((req, res, next) => {
  next();
});

router.all('/', (req, res) => {
  res.send("this is report root");
});

router.post('/create', reportCtrl.createReport);
router.get('/list', reportCtrl.showReportList);
router.get('/find/:reportId', reportCtrl.findReport);
router.delete('/delete/:reportId', reportCtrl.deleteReport);

module.exports = router;