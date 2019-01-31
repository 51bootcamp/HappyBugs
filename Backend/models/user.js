module.exports = (sequelize, DataTypes) => {
  const user = sequelize.define('user', {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      allowNull: false,
    },
    email: {
      type: DataTypes.STRING(255),
      unique: true,
      allowNull: false,
      validate: {
      isEmail: {msg : "it's not email"},
      }
    },
    password: {
      type: DataTypes.STRING
    }
  });

  user.associate = (models) => {
    user.hasMany(models.report, {foreignKey: 'userID', sourceKey: 'id'});
  };

  return user;
};
