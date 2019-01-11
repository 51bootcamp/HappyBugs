const express = require('express');
const router = express.Router();
const models = require('../../models');

router.use((req,res,next) => {
  console.log("user");
  next();
});

router.all('/', (req,res) => {
  res.send("this is user root");
})

router.post('/signin', (req, res) => {
  models.user.create({
      email : req.body.email,
      password : req.body.password,
  }).then(result => {
      console.log("everything is good");
  });
});

module.exports = router;
