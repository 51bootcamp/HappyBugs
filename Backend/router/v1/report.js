const express = require('express');
const router = express.Router();
const ctrl = require('./report.ctrl')

router.use((req,res,next) => {
  console.log("report router");
  next();
});

router.all('/', (req,res) => {
  res.send("this is report root");
});

router.post('/create', ctrl.create);
router.get('/list', ctrl.list);
router.get('/read/:report_ID', ctrl.read);
router.delete('/delete/:report_ID', ctrl.destroy);

module.exports = router;