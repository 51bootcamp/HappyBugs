const emailInfo = require('../../config/email_config.json');
const nodemailer = require('nodemailer');
const crypto = require('crypto');
const models = require('../../models')

let transporter = nodemailer.createTransport({
  service: 'gmail',
  secure: false,
  port: 25,
  auth: {
    user: emailInfo.id,
    pass: emailInfo.pass
  },
  tls: {
    rejectUnauthorized: false
  }
});

const maketoken = (user) => {
  let makedToken = crypto.randomBytes(100).toString('hex');

  models.verificationToken.create({
    userID: user.id,
    token: makedToken
  }).catch((err) => {
    console.log(err)
  });

  return makedToken;
};

const sendMail = (user) => {
  let link = "http://" + "localhost:80/api/v1/user" + "/verify?token=" + maketoken(user);
  let html =
  `
  <h1>Thank You!</h1>
  <p><strong>Please check your email</strong> for further instructions on how to complete your account setup.</p>
  <hr>
  <form action=${link} method="post" target="_blank">
    <div>
      <button>Verification</button>
    </div>
  </form>
  `

  let HelperOptions = {
    from: '"HEARYOU" <sssss>',
    to: user.email,
    subject: 'Welcome to HEARYOU!',
    text: 'This mail is for verification of your email!',
    html: html
  };

  transporter.sendMail(HelperOptions, (error, info) => {
    if (error) { return console.log(error); }
  });
};

const verify = (token) => {
  return models.verificationToken.findAll({
    where: {
      token: token
    }
  }).then(result => {
    if (result.length == 0) {
      return false;
    } else {
      models.user.update({
        isVerified: true
      },{
        where: {
          id: result[0].userID
        }
      }).then(models.verificationToken.destroy({
        where: {
          userID: result[0].userID
        }
      })).catch(err => console.log(err));

      return true;
    }
  }).catch(err => console.log(err));
}

module.exports = {
  sendMail,
  verify
};
