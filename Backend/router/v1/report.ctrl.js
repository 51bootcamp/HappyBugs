const models = require('../../models');

const create = (req, res) => {
  models.report.create({
    what: req.body.data[0].what,
    location: req.body.data[0].location,
    time: req.body.data[0].time,
    who: req.body.data[0].who,
    details: req.body.data[0].details,
  }).then(result => {
    console.log(result);
    console.log("success!");
  });
  res.status(201);
  res.send("good");
}

const list = (req, res) => {
  models.report.findAll().then((result) => {
    res.json(result);
  }).catch((err) => {
    console.log("error!!!!!!!!!!");
  });
}


const read = (req, res) => {
  const report_ID = parseInt(req.params.report_ID, 10) //decimal number
  //When ID is not a number
  if (Number.isNaN(report_ID)) {
    return res.status(400).end()
  }
  models.report.findAll({
    where: {
      id: report_ID
    }
  }).then((result) => {
    res.json(result);
  }).catch((err) => {
    console.log("error!!!!!!!!!!");
  });
}

const destroy = (req, res) => {
  const report_ID = parseInt(req.params.report_ID, 10) //decimal number
  //When ID is not a number
  if (Number.isNaN(report_ID)) {
    return res.status(400).end()
  }
  models.report.destroy({
    where: {
      id: report_ID
    }
  }).then((result) => {
    res.json({});
  }).catch((err) => {
    console.log("error!!!!!!!!!!");
  });
  res.status(204); //no content
}

module.exports = {
  create,
  list,
  read,
  destroy
}