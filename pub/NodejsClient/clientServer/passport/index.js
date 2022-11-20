const passport = require('passport');
const local = require('./localStrategy');
const Client = require('../models/Client');

module.exports = () => {
  passport.serializeUser((Client, done) => {
    done(null, Client.id);
  });

  passport.deserializeUser((id, done) => {
    Client.findOne({
      where: { id : id },
      attributes: ['id','nick','password']
    })
      .then(Client => done(null, Client))
      .catch(err => done(err));
  });

  local();
};
