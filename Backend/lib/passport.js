const models = require('../models');
const crypto = require('crypto')

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
          return done(null, false, {msg: 'incorrect user'});
        }
        if (hashPassword !== result[0].password) {
          return done(null, false, {msg: 'incorrect password'});
        }
        return done(null, result[0]);
      });
    }
  ));

  return passport;
};
