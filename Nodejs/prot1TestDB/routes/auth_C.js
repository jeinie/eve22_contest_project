const express = require('express');
const passport = require('passport');
const bcrypt = require('bcrypt');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');
const Client = require('../models/Client');

const router = express.Router();

router.post('/join', isNotLoggedIn, async (req, res, next) => {
  const { id, nick, password } = req.body;
    try {
        const exUser = await Client.findOne({ where: { id } });
        if (exUser) {
        return res.redirect('/join?error=exist');
        }
        const hash = await bcrypt.hash(password, 12);
        await Client.create({
          id,
          password: hash,
          nick,
        });
        return res.redirect('/');
    } catch (error) {
        console.error(error);
        return next(error);
    }
});

router.post('/login', isNotLoggedIn, (req, res, next) => {
  passport.authenticate('local', (authError, user, info) => {
    if (authError) {
      console.error(authError);
      return next(authError);
    }
    if (!user) {
      console.log("??");
      return res.redirect(`/?loginError=${info.message}`);
    }
    return req.login(user, (loginError) => {
      console.log(`user : ${user}`);
      if (loginError) {
        console.error(loginError);
        return next(loginError);
      }
      return res.redirect('/');
    });
  })(req, res, next); // 미들웨어 내의 미들웨어에는 (req, res, next)를 붙입니다.
});

//회원탈퇴
router.get('/delUser/:userid', isLoggedIn, async (req, res) => {
  const userid = req.params.userid;
  try {
    const exUser = await Client.findOne({ where: { id : userid } });
    if (exUser) {
      await Client.destroy({
        where:{
          id: userid
        }
      })
      return res.redirect('/');   
    }
  } catch (error) {
    console.error(error);
    return error;
  }
});
//회원탈퇴

router.get('/logout', isLoggedIn, (req, res) => {
  req.logout();
  req.session.destroy();
  res.redirect('/');
});


module.exports = router;