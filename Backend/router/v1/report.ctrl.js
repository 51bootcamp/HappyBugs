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

const findReport = (req, res) => {
  const reportId = parseInt(req.params.reportId)
  //When ID is not a number
  if (Number.isNaN(reportId)) {
    return res.status(400).end()
  }
  models.report.findAll({
    where: {
      id: reportId
    }
  }).then((result) => {
    res.json(result);
  }).catch((err) => {});
}

const deleteReport = (req, res) => {
  const reportId = parseInt(req.params.reportId)
  //When ID is not a number
  if (Number.isNaN(reportId)) {
    return res.status(400).end()
  }
  models.report.destroy({
    where: {
      id: reportId
    }
  }).then((result) => {
    res.status(204).json(result);  //no content
  }).catch((err) => {});
}

module.exports = {
  createReport,
  showReportList,
  findReport,
  deleteReport
}