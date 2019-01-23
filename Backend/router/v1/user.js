const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto');
const session = require('express-session');

let MIN_PASSWORD_LENGTH = 6;
let MAX_PASSWORD_LENGTH = 16;

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

router.post('/signup', (req, res) => {
  let hashedPassword = crypto.createHash("sha512").update(req.body.password).digest("hex");

  models.user.findAll({
    where: {
      email: req.body.email
    }
  }).then(result => {
    if (result == "") {
      if (req.body.password.length < MIN_PASSWORD_LENGTH || req.body.password.length > MAX_PASSWORD_LENGTH ) {
        res.status(400).send("Password must be between " + MIN_PASSWORD_LENGTH + ' and ' + MAX_PASSWORD_LENGTH + " characters long");
      } else {
        models.user.create({
          email: req.body.email,
          password: hashedPassword,
        }).then(result => {
            res.send(
              "< " + req.body.email + " > membership has been completed."
            );
        });
      }
    } else {
      res.send(
        "< " + req.body.email + " > are already a member"
      );
    }
  }).catch(err => {
    res.send("err");
  });
});

router.get('/signin', (req, res) => {
  let session = req.session;

  models.user.findAll({
    where: {
      email: req.query.email
    }
  }).then(result => {
    if (result == "")
    {
      res.json({msg: "User does not exist"});
    } else {
      if (req.body.password.length < MIN_PASSWORD_LENGTH || req.body.password.length > MAX_PASSWORD_LENGTH ) {
        res.status(400).send("Password must be between " + MIN_PASSWORD_LENGTH + ' and ' + MAX_PASSWORD_LENGTH + " characters long");
      } else {
        let dbPassword = result[0].dataValues.password;
        let hashPassword = crypto.createHash("sha512").update(req.query.password).digest("hex");
        if(dbPassword == hashPassword) {
          req.session.email = req.query.email;
          res.json({msg: req.session.email});
        } else {
          res.json({msg: "Password not Match"});
        };
      }
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
