const express = require('express');
const app = express();
const models = require('./models');
const bodyParser = require('body-parser');
const router = express.Router();
const session = require('express-session');
const sessionSet = require('./config/session.json');
const config = require('./config/server_config.json');

models.sequelize.sync();

app.use(session({
  secret: sessionSet.setting.salt,
  cookie: sessionSet.setting.cookie,
  resave: sessionSet.setting.resave,
  saveUninitialized: sessionSet.setting.saveUninitialized
}));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true,
}));

const passport = require('./lib/passport')(app);
const rootRouter = require('./router/index')(passport);

app.use('/api', rootRouter);

app.listen(config.port || 80, () => {
  console.log("server start :", config.port)
});
