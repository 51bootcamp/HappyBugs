const models = require('../models');
const crypto = require('crypto');

const loginLimitCount = 3;
const restrictTime = 30;

module.exports = (app) => {
  const passport = require('passport');
  LocalStrategy = require('passport-local').Strategy;

  app.use(passport.initialize());
  app.use(passport.session());

  passport.serializeUser((user, done) => {
    done(null, user.email);
  });

  passport.deserializeUser((id, done) => {
    models.user.findAll({
      where: {
        email: id
      }
    })
      .then(user => done(null, user))
      .catch(e => done(e, false))
  });

  passport.use(new LocalStrategy(
    {
      usernameField: 'email',
      passwordField: 'password',
      passReqToCallback: true
    },
    (req, username, password, done) => {
      let hashedPassword = crypto.createHash("sha512").update(req.query.password).digest("hex");
      models.user.findAll({
        where: {
          email: req.query.email
        }
      }).then(result => {
        if (result == "") {
          return done(null, false, {msg: 'Incorrect user'});
        }
        // Check email is verified
        if (result[0].isVerified == false) {
          return done(null, false, {msg: 'This email is not verified'});
        }
        // Check number of login attempts
        // Last login attempt time - current login attempt time
        // Limit login attempt to a 30 minute
        if (result[0].loginFailCount == loginLimitCount && ((Date.now() - Number(new Date(result[0].updatedAt)))/100000) < restrictTime) {
          return done(null, false, {msg: 'Login restricted'});
        }
        if (hashedPassword !== result[0].password) {
          return done(null, false, {msg: 'Incorrect id or password'});
        }
        return done(null, result[0]);
      });
    }
  ));

  return passport;
};
