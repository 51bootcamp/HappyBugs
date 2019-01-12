module.exports = (sequelize, DataTypes) => {
  return sequelize.define('user', {
    id: {
      type: DataTypes.INTEGER,
      primaryKey : true,
      autoIncrement : true,
      allowNull : false,
    },
    email: {
      type: DataTypes.STRING(255),
      unique : true,
      allowNull : false,
      validate : {
        isEmail : {msg : "it's not email"},
      }
    },
    password : {
      type : DataTypes.STRING
    }
  });
};
