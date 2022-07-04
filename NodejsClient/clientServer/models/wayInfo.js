const Sequelize = require('sequelize');

module.exports = class wayInfo extends Sequelize.Model {
  static init(sequelize) {
    return super.init({
      노선명: {
        type: Sequelize.STRING(20),
        allowNull: false,    
      },
      순번: {
        type: Sequelize.STRING(7),
        allowNull: false,
      },
      NODE_ID:{
        type: Sequelize.STRING(20),
        allowNull: false,
      },
      'ARS-ID':{
        type: Sequelize.STRING(10),
        allowNull: false,
      },
      정류소명:{
        type: Sequelize.INTEGER(100),
        allowNull: false,
      }
    }, {
      sequelize,
      timestamps: true,
      underscored: false,
      modelName: 'wayInfo',
      tableName: 'wayInfo',
      paranoid: true,
      charset: 'utf8',
      collate: 'utf8_general_ci',
    });
  }

  static associate(db) {
  //나중에 추가할수도
    db.wayInfo.belongsToMany(db.way, {through : 'busWays'});
  }
};
