const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto')

const MIN_PASSWORD_LENGTH = 8;

module.exports = (passport) => {
  router.use((req, res, next) => {
    next();
  });

  router.all('/', (req, res) => {
    res.status(404);
  });

  router.get('/signin', (req, res, next) => {
    passport.authenticate('local', {
      successRedirect : '/api/v1/user/signin-success',
      failureRedirect : '/api/v1/user/signin-failure'
    }) (req,res,next);
  });

  router.get('/signin-success', (req, res) => {
    res.status(200);
    res.json({
      msg: "Signin success"
    });
  });

  router.get('/signin-failure', (req, res) => {
    res.status(401);
    res.json({
      msg: "Signin failed"
    });
  });

  router.get('/signout', (req, res) => {
    if(req.isAuthenticated()){
      res.status(200);
      req.session.destroy();
      res.clearCookie('sid');
      res.json({
        msg: "Signout success"
      });
    } else {
      res.status(403);
      res.json({
        msg: "Signout failed"
      });
    }
  });

  router.post('/signup', (req, res) => {

    let hashedPassword = crypto.createHash("sha512").update(req.body.password).digest("hex");

    models.user.findAll({
      where: {
        email: req.body.email
      }
    }).then(result => {
      // If data is not found. The result is null.
      if (result == "") {
        if((req.body.password.length < MIN_PASSWORD_LENGTH)) {
          res.status(400).json({
            msg :"Password must be at least " + MIN_PASSWORD_LENGTH
          });
        } else {
          models.user.create({
            email : req.body.email,
            password : hashedPassword
          }).then(result => {
              res.status(201).json({
                msg: "Account successfully created"
              });
          });
        }
      } else {
        res.status(409).json({
          msg: "This member already exists"
        })
      }
    }).catch(err => {
      res.send(err);
    });
  });

  return router;
};
