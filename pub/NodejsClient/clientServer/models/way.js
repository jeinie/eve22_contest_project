const Sequelize = require('sequelize');

module.exports = class way extends Sequelize.Model {
  static init(sequelize) {
    return super.init({
      노선명: {
        type: Sequelize.STRING(20),
        primaryKey: true,
      },
      노선ID: {
        type: Sequelize.STRING(20),
        allowNull: false,
      }
    }, {
      sequelize,
      timestamps: true,
      underscored: false,
      modelName: 'way',
      tableName: 'way',
      paranoid: true,
      charset: 'utf8',
      collate: 'utf8_general_ci',
    });
  }

  static associate(db) {
    db.way.hasMany(db.wayInfo);
  }
};
