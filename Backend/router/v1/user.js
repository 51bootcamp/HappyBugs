const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto')

router.use((req,res,next) => {
  console.log("user router");
  next();
});

router.all('/', (req,res) => {
  res.send("this is user root");
})

router.post('/signup', (req, res) => {
  let hashPassword = crypto.createHash("sha512").update(req.body.password).digest("hex");

  models.user.create({
      email : req.body.email,
      password : hashPassword,

  }).then(result => {
      console.log("everything is good");
      res.send(result);
  });
});

module.exports = router;
