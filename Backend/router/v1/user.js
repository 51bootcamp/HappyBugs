const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto');
const session = require('express-session');

router.use(session({
  key: 'sid',
  secret: 'secret',
  resave: false,
  saveUninitialized: true,
  cookie: {
    maxAge: 1000*60*15
  }
}));

router.use((req, res, next) => {
  console.log("user router");
  next();
});

router.all('/', (req, res) => {
  res.send("this is user root");
});

router.get('/signin', (req, res) => {
  let session = req.session;

  models.user.findAll({
    where: {
      email: req.query.email
    }
  }).then(result => {
    if (result == "") {
      res.json({msg: "User does not exist"});
    } else {
      let dbPassword = result[0].dataValues.password;
      let hashPassword = crypto.createHash("sha512").update(req.query.password).digest("hex");
      if(dbPassword == hashPassword) {
        req.session.email = req.query.email;
        res.json({msg: req.session.email});
      } else {
        res.json({msg: "Password not Match"});
      };
    };
  }).catch( err => {
    res.json({msg: "err"});
  });
});

router.get('/signout', (req, res) => {
  req.session.destroy();
  res.clearCookie('sid');
});

module.exports = router;
