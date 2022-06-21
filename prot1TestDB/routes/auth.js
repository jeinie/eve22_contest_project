const express = require('express');
const passport = require('passport');
const bcrypt = require('bcrypt');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');
const User = require('../models/user');

const router = express.Router();

router.get('/join/:id/:password', isNotLoggedIn, async (req, res, next) => {
    //console.log(req.body.data);
    const id = req.params.id;
    const password = req.params.password;

    try {
        const exUser = await User.findOne({ where: { id } });
        if (exUser) {
        return res.redirect('/join?error=exist');
        }
        const hash = await bcrypt.hash(password, 12);
        await User.create({
        id,
        password: hash,
        });
        return res.redirect('/');
    } catch (error) {
        console.error(error);
        return next(error);
    }
});

router.get('/login/:id/:password', isNotLoggedIn, (req, res, next) => {
  passport.authenticate('local', (authError, user, info) => {
    if (authError) {
      console.error(authError);
      return next(authError);
    }
    if (!user) {
      return res.redirect(`/?loginError=${info.message}`);
    }
    return req.login(user, (loginError) => {
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
    const exUser = await User.findOne({ where: { id : userid } });
    if (exUser) {
      await User.destroy({
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