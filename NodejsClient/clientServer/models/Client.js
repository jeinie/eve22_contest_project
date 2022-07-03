const Sequelize = require('sequelize');

module.exports = class Client extends Sequelize.Model {
  static init(sequelize) {
    return super.init({
      id: {
        type: Sequelize.STRING(15),
        primaryKey: true,
      },
      password: {
        type: Sequelize.STRING(100),
        allowNull: false,
      },
      nick:{
        type: Sequelize.STRING(15),
        allowNull: false,
      },
      name:{
        type: Sequelize.STRING(15),
        allowNull: false,
      },
      age:{
        type: Sequelize.INTEGER(15),
        allowNull: false,
      },
      cardnum:{
        type: Sequelize.STRING(40),
        allowNull: true,
      },
      busId:{
        type: Sequelize.STRING(15),
        allowNull: true,
      }
    }, {
      sequelize,
      timestamps: true,
      underscored: false,
      modelName: 'Client',
      tableName: 'Clients',
      paranoid: true,
      charset: 'utf8',
      collate: 'utf8_general_ci',
    });
  }

  //static associate(db) {
  //나중에 추가할수도
  //}
};
