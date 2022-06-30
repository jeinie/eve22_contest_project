const express = require('express');
const passport = require('passport');
const bcrypt = require('bcrypt');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');
const Driver = require('../models/Driver');

const router = express.Router();

async function resMsg(result,res){
  return res.json(result);
}

router.post('/join', isNotLoggedIn, async (req, res, next) => {
  const { id,password,nick,name } = req.body;
  
    try {    
        const exUser = await Driver.findOne({ where: { id : id } });
        const result = {};
        if (exUser) {
        //return res.redirect('/join?error=exist');
          result["success"] = "100";
          result["msg"] = "regist failed";
          await resMsg(result,res);
          return false;
        }
        const hash = await bcrypt.hash(password, 12);
        await Driver.create({
          id,
          password: hash,
          nick,
          name,
        });
        result["success"] = "200";
        result["msg"] = "regist successed";
        //res.json(result);
        await resMsg(result,res);
    } catch (error) {
        console.error(error);
        return next(error);
    }
});

router.post('/login', isNotLoggedIn, (req, res, next) => {
  passport.authenticate('local', async (authError, user, info) => {
    const result = {};
    if (authError) {
      console.error(authError);
      return next(authError);
    }
    if (!user) {
      result["success"] = "100";
      result["msg"] = "login failed";
      await resMsg(result,res);
      //return res.redirect(`/?loginError=${info.message}`);
    }
    return req.login(user, async (loginError) => {
      if (loginError) {
        console.error(loginError);
        return next(loginError);
      }
      result["id"] = user["id"];
      result["nick"] = user["nick"];
      result["name"] = user["name"];
      result["age"] = user["age"];
      result["success"] = "200";
      result["msg"] = "login successed";
      await resMsg(result,res);
    });
  })(req, res, next); // 미들웨어 내의 미들웨어에는 (req, res, next)를 붙입니다.
});

//회원탈퇴
router.get('/delUser/:userid', isLoggedIn, async (req, res) => {
  const userid = req.params.userid;
  try {
    const exUser = await Driver.findOne({ where: { id : userid } });
    if (exUser) {
      await Driver.destroy({
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