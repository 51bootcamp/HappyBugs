const models = require('../../models');

const createReport = (req, res) => {
  if(req.isAuthenticated()){
    models.report.create({
      what: req.body.data[0].what,
      location: req.body.data[0].location,
      time: req.body.data[0].time,
      who: req.body.data[0].who,
      details: req.body.data[0].details,
      userID: req.user[0].dataValues.id
    }).then((result) => {
      res.status(201);
      res.json({
        msg: "Report is made successfully"
      });
    });
  } else {
    res.status(403);
    res.json({
      msg : "You are not logged in"
    });
  }
};

const showReportList = (req, res) => {
  if(req.isAuthenticated()){
    models.report.findAll({
      where: {
        userID: req.user[0].dataValues.id
      }
    }).then((result) => {
      res.status(200);
      res.json(result);
    }).catch((err) => {});
  } else{
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

  if(req.isAuthenticated()){
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

  models.report.update({
      what: newWhat,
      location: newLocation,
      time: newTime,
      who: newWho,
      details: newDetails
    },{
      where: {id: reportId}
  }).then((result) => {
    if(result == ""){
      res.json({msg: "ReportId not exist"});
    } else {
      res.send("good");
    };
  }).catch((err) => {
    res.json({msg: "error"});
  });
}

module.exports = {
  createReport,
  showReportList,
  findReport,
  deleteReport,
  editReport
};
