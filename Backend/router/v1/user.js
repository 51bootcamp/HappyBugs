const express = require('express');
const router = express.Router();
const models = require('../../models');
const crypto = require('crypto');
const Sequelize = require('sequelize');

const minPasswordLength = 8;
const loginLimitCount = 3;

module.exports = (passport) => {
  router.use((req, res, next) => {
    next();
  });

  router.all('/', (req, res) => {
    res.status(404);
  });

  router.get('/signin', (req, res, next) => {
    passport.authenticate('local', {
      successRedirect : '/api/v1/user/signin-success' + '?email=' + req.query.email,
      failureRedirect : '/api/v1/user/signin-failure' + '?email=' + req.query.email
    }) (req,res,next);
  });

  router.get('/signin-success', (req, res) => {
    models.user.update({
      loginFailCount : 0
    } ,{
      where:
      {
        email: req.query.email
      }
    }).catch((err) => {
      res.status(520).json({
        statusCode: 4000
      });
    });
    res.status(200);
    res.json({
      statusCode: 2001
    });

  });

  router.get('/signin-failure', (req, res) => {
    models.user.findAll({
      where:
      {
        email: req.query.email
      }
    }).then(result => {
      if( result == "" ) {
        res.status(401);
        res.json({
          statusCode: 3012
        });
      } else if( result[0].loginFailCount==loginLimitCount ) {
        res.status(401);
        res.json({
          statusCode: 3011
        });
      } else {
        models.user.update({
          loginFailCount : Sequelize.literal('loginFailCount + 1')
        } ,{
          where:
          {
            email: req.query.email
          }
        });
        res.status(401);
        res.json({
          statusCode: 3001
        });
      };
    }).catch((err) => {
      res.status(520).json({
        statusCode: 4000
      });
    });
  });

  router.get('/signout', (req, res) => {
    if(req.isAuthenticated()){
      res.status(200);
      req.session.destroy();
      res.clearCookie('sid');
      res.json({
        statusCode: 2002
      });
    } else {
      res.status(403);
      res.json({
        statusCode: 3002
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
        if((req.body.password.length < minPasswordLength)) {
          res.status(400).json({
            statusCode: 3003
          });
        } else {
          models.user.create({
            email : req.body.email,
            password : hashedPassword,
            loginFailCount : 0
          }).then(result => {
              res.status(201).json({
                statusCode: 2003
              });
          }).catch((err) => {
            res.status(400).json({
              statusCode: 3013
            });
          });
        }
      } else {
        res.status(409).json({
          statusCode: 3004
        })
      }
    }).catch((err) => {
      res.status(520).json({
        statusCode: 4000
      });
    });
  });


  return router;
};
