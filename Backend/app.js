const express = require('express');
const app = express();
// manage db
const models = require('./models');
// routers
const router = express.Router();
const rootrouter = require('./router/index');

// to get json data from body
const bodyParser = require('body-parser');

models.sequelize.sync();

//json encoded
app.use(bodyParser.json());
//url encoded
app.use(bodyParser.urlencoded({
  extended: true,
}));

app.use('/api', rootrouter);

app.listen(3000, function(){
  console.log("Server Start");
});
