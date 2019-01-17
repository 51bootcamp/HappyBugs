const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto')

router.use((req, res, next) => {
  console.log("user router");
  next();
});

router.all('/', (req, res) => {
  res.send("this is user root");
});

router.post('/signup', (req, res) => {

  let hashPassword = crypto.createHash("sha512").update(req.body.password).digest("hex");

  models.user.findAll({
    where: {
      email: req.body.email
    }

  }).then(result => {
    if (result==""){

      models.user.create({
        email : req.body.email,
        password : hashPassword,
      }).then(result => {
          console.log("< " + req.body.email + " > membership has been completed.");
          res.send(
            "< " + req.body.email + " > membership has been completed."
          );
      });
    } else {
      console.log("< " + req.body.email + " > are already a member");
      res.send(
        "< " + req.body.email + " > are already a member"
      );
    }
  }).catch(err => {
    console.log(err);
  });

});

module.exports = router;
