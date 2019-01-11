const express = require('express');
const router = express.Router();

let version = 'v1';

const userrouter = require('./' + version + '/user');
const reportrouter = require('./' + version + '/report');

router.use((req,res,next) => {
  console.log("index page");
  console.log(req.path);
  next();
});


router.all('/' + version + '/user', userrouter);
router.all('/' + version + '/report', reportrouter);

module.exports = router;
