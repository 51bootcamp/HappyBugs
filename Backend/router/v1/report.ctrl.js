const models = require('../../models');

const createReport = (req, res) => {
  if (!req.isAuthenticated()) {
    return res.status(403).json({statusCode: 3005});
  }

  let reportedID;

  models.perpetrator.findOrCreate({
    where: {
      facebook_url: req.body.data[0].facebook_url
    }
  }).then((result) => {
    models.report.create({
      what: req.body.data[0].what,
      location: req.body.data[0].location,
      time: req.body.data[0].time,
      who: req.body.data[0].who,
      details: req.body.data[0].details,
      perpetratorID: result[0].id,
      userID: req.user[0].dataValues.id
    }).then((result) => {
      reportedID = result.id;

      models.report.count({
        group: ['userID', 'perpetratorID'],
        attributes: ['userID', 'perpetratorID'],
        where: {
          perpetratorID: result.perpetratorID
        }
      }).then((count) => {
          models.perpetrator.update({
            reporting_user_count: count.length
          },{
            where: {
              id: count[0].perpetratorID
            }
          });
          res.status(201).json({
            id: reportedID
          });
        });
    });
  });
};

const showReportList = (req, res) => {
  if (!req.isAuthenticated()) {
    return res.status(403).json({statusCode: 3005});
  }
  models.report.findAll({
    where: {
      userID: req.user[0].dataValues.id
    }
  }).then((result) => {
    res.status(200);
    res.json({data: result});
  }).catch((err) => {
    res.json({statusCode: 3007});
  });
};

const findReport = (req, res) => {
  if (!req.isAuthenticated()) {
    return res.status(403).json({statusCode: 3005});
  }
  const reportId = parseInt(req.query.reportId);
  if (Number.isNaN(reportId)) {
    return res.status(400).end();
  }
  models.report.findAll({
    where: {
      id: reportId
    }
  }).then ((result) => {
    if (result == 0) {
      res.json({statusCode: 1002});
    } else {
      res.json({data: result});
    }
  }).catch((err) => {
    res.json({statusCode: 3008});
  });
};

const deleteReport = (req, res) => {
  if (!req.isAuthenticated()) {
    return res.status(403).json({statusCode: 3005});
  }
  const reportId = parseInt(req.query.reportId);
  if (Number.isNaN(reportId)) {
    return res.status(400).end();
  }
  models.report.destroy({
    where: {
      id: reportId
    }
  }).then((result) => {
    if (result == 0) {
      res.json({statusCode: 1002});
    } else {
      res.status(204).end();
    }
  }).catch((err) => {
    res.json({statusCode: 3009});
  });
};

const editReport = (req, res) => {
  if (!req.isAuthenticated()) {
    return res.status(403).json({statusCode: 3005});
  }
  const reportId = parseInt(req.query.reportId);
  if (Number.isNaN(reportId)) {
    return res.status(400).end();
  }
  const newWhat = req.body.data[0].what;
  const newLocation = req.body.data[0].location;
  const newTime = req.body.data[0].time;
  const newWho = req.body.data[0].who;
  const newDetails = req.body.data[0].details;

  models.report.update({
      what: newWhat,
      location: newLocation,
      time: newTime,
      who: newWho,
      details: newDetails
    },{
      where: {id: reportId}
  }).then((result) => {
    if (result == 0) {
      res.json({statusCode: 1002});
    } else {
      res.status(200).end();
    }
  }).catch((err) => {
    res.json({statusCode: 3010});
  });
};

module.exports = {
  createReport,
  showReportList,
  findReport,
  deleteReport,
  editReport
};