module.exports = (sequelize, DataTypes) => {
	const verificationToken = sequelize.define('verificationToken', {
		id: {
			allowNull: false,
			autoIncrement: true,
			primaryKey: true,
			type: DataTypes.INTEGER
		},
		token: {
			type: DataTypes.STRING,
			allowNull: false
		}
	});

	verificationToken.associate = (models) => {
	  verificationToken.belongsTo(models.user, {foreignKey: 'userID', sourceKey: 'id'});
	};

	return verificationToken;
};
