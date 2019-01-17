const models = require('../../models');

const createReport = (req, res) => {
  models.report.create({
    what: req.body.data[0].what,
    location: req.body.data[0].location,
    time: req.body.data[0].time,
    who: req.body.data[0].who,
    details: req.body.data[0].details,
  }).then((result) => {
    res.status(201);
    res.send("good");
  });
}

const showReportList = (req, res) => {
  models.report.findAll().then((result) => {
    res.json(result);
  }).catch((err) => {});
}

const readReport = (req, res) => {
  const report_ID = parseInt(req.params.report_ID)
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
  }).catch((err) => {});
}

const destroyReport = (req, res) => {
  const report_ID = parseInt(req.params.report_ID)
  //When ID is not a number
  if (Number.isNaN(report_ID)) {
    return res.status(400).end()
  }
  models.report.destroy({
    where: {
      id: report_ID
    }
  }).then((result) => {
    res.status(204); //no content
  }).catch((err) => {});
}

module.exports = {
  createReport,
  showReportList,
  readReport,
  destroyReport
}