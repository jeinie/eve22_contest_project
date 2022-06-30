const passport = require('passport');
const local = require('./localStrategy');
const Driver = require('../models/Driver');

module.exports = () => {
  passport.serializeUser((Driver, done) => {
    done(null, Driver.id);
  });

  passport.deserializeUser((id, done) => {
    Driver.findOne({
      where: { id : id },
      attributes: ['id','nick','password']
    })
      .then(Driver => done(null, Driver))
      .catch(err => done(err));
  });

  local();
};
