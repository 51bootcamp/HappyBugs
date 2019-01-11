const express = require('express');
const app = express();
const models = require('./models');
const router = express.Router();
const rootRouter = require('./router/index');
const bodyParser = require('body-parser');

models.sequelize.sync();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true,
}));

app.use('/api', rootRouter);

app.listen(3000, () => {
  console.log("Server Start");
});
