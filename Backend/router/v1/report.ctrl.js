const models = require('../../models');

const createReport = (req, res) => {
  if (!req.isAuthenticated()) {
    res.status(403);
    res.json({
      msg : "you are not logined"
    });
    res.end();
    return ;
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
  if (req.isAuthenticated()) {
    models.report.findAll({
      where: {
        userID: req.user[0].dataValues.id
      }
    }).then((result) => {
      res.status(200);
      res.json(result);
    }).catch((err) => {});
  } else {
    res.status(401)
    res.json({
      msg: "get report failed"
    })
  }
};

const findReport = (req, res) => {
  if (req.isAuthenticated()) {
    const reportId = parseInt(req.query.reportId);

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
  } else {
    res.json({
      msg: "Can't find this report"
    });
  }
};

const deleteReport = (req, res) => {
  const reportId = parseInt(req.params.reportId);
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
};

const editReport = (req, res) => {
  const reportId = parseInt(req.query.reportId);
  //When ID is not a number
  if (Number.isNaN(reportId)) {
    return res.status(400).end()
  }

  const newWhat = req.body.data[0].what;
  const newLocation = req.body.data[0].location;
  const newTime = req.body.data[0].time;
  const newWho = req.body.data[0].who;
  const newDetails = req.body.data[0].details;

  if (req.isAuthenticated()) {
    models.report.update({
        what: newWhat,
        location: newLocation,
        time: newTime,
        who: newWho,
        details: newDetails
      },{
        where: {id: reportId}
    }).then((result) => {
      res.status(200);
      res.json({
        msg: "update success"
      });
    }).catch((err) => {
      res.json({
        msg: "update fail"
      });
    });
  } else {
    res.status(401);
    res.json({
      msg: "you are not logged in"
    });
  }
};

module.exports = {
  createReport,
  showReportList,
  findReport,
  deleteReport,
  editReport
};
