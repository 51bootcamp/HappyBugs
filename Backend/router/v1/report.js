const express = require('express');
const router = express.Router();
const models = require('../../models');

router.use((req,res,next) => {
  console.log("report router");
  next();
});

router.all('/', (req,res) => {
  res.send("this is report root");
});

router.post('/create', (req, res) => {
  models.report.create({
      what : req.body.data[0].what,
      location : req.body.data[0].location,
      time : req.body.data[0].time,
      who : req.body.data[0].who,
      details : req.body.data[0].details,    
  }).then(result => {
      console.log(result);
      console.log("everything is good");
  });

  res.send("gj");
});

module.exports = router;
