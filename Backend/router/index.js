const express = require('express');
const router = express.Router();

const version = 'v1';
const userrouter = require('./' + version + '/user');
const reportrouter = require('./' + version + '/report');

router.use((req, res, next) => {
  console.log("index page");
  console.log(req.path);
  next();
});

router.use('/' + version + '/user', userrouter);
router.use('/' + version + '/report', reportrouter);

module.exports = router;
