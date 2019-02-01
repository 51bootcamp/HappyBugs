module.exports = (sequelize, DataTypes) => {
  const perpetrator = sequelize.define('perpetrator', {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      allowNull: false,
    },
    reporting_user_count: {
      type: DataTypes.INTEGER,
      allowNull: true,
      defaultValue: 0
    },
    facebook_url: {
      type: DataTypes.STRING,
    }
  });

  perpetrator.associate = (models) => {
    perpetrator.hasMany(models.report, {foreignKey: 'perpetratorID', sourceKey: 'id'});
  };

  return perpetrator;
};
