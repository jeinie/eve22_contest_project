const Sequelize = require('sequelize');
const env = process.env.NODE_ENV || 'development';
const config = require('../config/config')[env];

const Client = require('./Client');
const way = require('./way');
const wayInfo = require('./wayInfo');

const db = {};

const sequelize = new Sequelize(
  config.database, config.username, config.password, config,
);

db.sequelize = sequelize;
db.Client = Client;
db.way = way;
db.wayInfo = wayInfo;

Client.init(sequelize);
way.init(sequelize);
wayInfo.init(sequelize);
//나중에 추가되면 사용할수도
//User.associate(db);

way.associate(db);
wayInfo.associate(db);

module.exports = db;
