const express = require('express');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');
const { User } = require('../models');

const router = express.Router();

router.use((req, res, next) => {
  res.locals.user = req.user;
  next();
});

router.get('/', async (req, res, next) => {
  try {
    //const user = await User.findOne({where : {id : }})
    res.render('index.ejs', {
      title: 'eveProj',
    });
  } catch (err) {
    console.error(err);
    next(err);
  }
});

router.get('/join',(req,res)=>{
  res.render('join.ejs',{
    title: 'eveProj'
  })
})

module.exports = router;
