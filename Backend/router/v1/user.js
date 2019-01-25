const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto')

const MIN_PASSWORD_LENGTH = 6;

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
      msg: "signin success"
    });
  });

  router.get('/signin-failure', (req, res) => {
    res.status(401);
    res.json({
      msg: "signin failed"
    });
  });

  router.get('/signout', (req, res) => {
    if(req.isAuthenticated()){
      res.status(200);
      req.session.destroy();
      res.clearCookie('sid');
      res.json({
        msg: "signout success"
      });
    } else {
      res.status(403);
      res.json({
        msg: "signout failed"
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
        models.user.create({
          email : req.body.email,
          password : hashedPassword
        }).then(result => {
            res.status(201).json({
              msg: "Creating ID success"
            });
        });
      } else {
        res.status(409).json({
          msg: "Creating ID failed"
        })
      }
    }).catch(err => {
      res.send(err);
    });
  });

  return router;
};
