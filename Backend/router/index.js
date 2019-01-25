const express = require('express');
const router = express.Router();

module.exports = (passport) => {
  const version = 'v1';
  const userrouter = require('./' + version + '/user')(passport);
  const reportrouter = require('./' + version + '/report')();

  router.use((req, res, next) => {
    next();
  });

  router.use('/' + version + '/user', userrouter);
  router.use('/' + version + '/report', reportrouter);

  return router;
};
