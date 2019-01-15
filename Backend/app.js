const express = require('express');
const app = express();
// Manage db
const models = require('./models');
// Routers
const router = express.Router();
const rootRouter = require('./router/index');

// To get json data from body
const bodyParser = require('body-parser');

models.sequelize.sync();

//Json encoded
app.use(bodyParser.json());
//Url encoded
app.use(bodyParser.urlencoded({
  extended: true,
}));

app.use('/api', rootRouter);

app.listen(3000, function(){
  console.log("Server Start");
});
