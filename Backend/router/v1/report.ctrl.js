const models = require('../../models');

function updatePerpetrator (perpetratorID) {
  models.report.count({
    group: ['userID', 'perpetratorID'],
    attributes: ['userID', 'perpetratorID'],
    where: {
      perpetratorID: perpetratorID
    }
  }).then((count) => {
    models.perpetrator.update({
      reporting_user_count: count.length
    },{
      where: {
        id: perpetratorID
      }
    });
  });
}

const createReport = (req, res) => {
  if (!req.isAuthenticated()) {
    return res.status(403).json({statusCode: 3005});
  }

  models.perpetrator.findOrCreate({
    where: {
      facebook_url: req.body.data[0].facebook_url
    }
  }).then((result) => {
    let perpetrator_id = null;
    if (result[0].facebook_url) {
      perpetrator_id = result[0].id;
    }
    models.report.create({
      what: req.body.data[0].what,
      location: req.body.data[0].location,
      time: req.body.data[0].time,
      who: req.body.data[0].who,
      details: req.body.data[0].details,
      perpetratorID: perpetrator_id,
      userID: req.user[0].dataValues.id
    }).then((createResult) => {
      updatePerpetrator(createResult.perpetratorID);
      res.status(201).json({id: createResult.id});
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
    },
    include: [{
      model: models.perpetrator,
      attributes: ['facebook_url', 'reporting_user_count']
    }],
    attributes: ['id', 'what', 'location', 'who', 'time', 'details', 'type', 'createdAt', 'updatedAt']
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
      id: reportId,
      userID: req.user[0].dataValues.id
    },
    include: [{
      model: models.perpetrator,
      attributes: ['facebook_url', 'reporting_user_count']
    }],
    attributes: ['id', 'what', 'location', 'who', 'time', 'details', 'type', 'createdAt', 'updatedAt']
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
  const userId = req.user[0].dataValues.id;
  if (Number.isNaN(reportId)) {
    return res.status(400).end();
  }
  
  models.report.findAll({
    where: {
      id: reportId,
      userID: userId
    }
  }).then((result) => {
    if (result == 0) {
      res.json({statusCode: 1002});
    } else {
      let perpetrator_id = result[0].perpetratorID;

      models.report.destroy({
        where: {
          id: reportId,
          userID: userId
        }
      }).then((destroyResult) => {
        updatePerpetrator(perpetrator_id);
        res.status(204).end();
      });
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
  const userId = req.user[0].dataValues.id;
  if (Number.isNaN(reportId)) {
    return res.status(400).end();
  }
  const newWhat = req.body.data[0].what;
  const newLocation = req.body.data[0].location;
  const newTime = req.body.data[0].time;
  const newWho = req.body.data[0].who;
  const newDetails = req.body.data[0].details;
  const newFacebookUrl = req.body.data[0].facebook_url;

  models.report.findAll({
    where: {
      id: reportId,
      userID: userId
    }
  }).then ((result) => {
    if (result == 0) {
      res.json({statusCode: 1002});
    } else {
      let pastFacebookId = result[0].perpetratorID;

      models.perpetrator.findOrCreate({
        where: {
          facebook_url: newFacebookUrl
        }
      }).then((createResult) => {
        let perpetrator_id = null;
        if (createResult[0].facebook_url) {
          perpetrator_id = createResult[0].id;
        }
        models.report.update({
          what: newWhat,
          location: newLocation,
          time: newTime,
          who: newWho,
          details: newDetails,
          perpetratorID: perpetrator_id
        },{
          where: {
            id: reportId,
            userID: userId
          }
        }).then((result) => {
          updatePerpetrator(perpetrator_id);
          updatePerpetrator(pastFacebookId);
          res.status(200).end();
        });
        
      });
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
