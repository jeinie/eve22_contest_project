const Sequelize = require('sequelize');
const env = process.env.NODE_ENV || 'development';
const config = require('../config/config')[env];

const Driver = require('./Driver');

const db = {};

const sequelize = new Sequelize(
  config.database, config.username, config.password, config,
);

db.sequelize = sequelize;
db.Driver = Driver;

Driver.init(sequelize);

//나중에 추가되면 사용할수도
//User.associate(db);

module.exports = db;
