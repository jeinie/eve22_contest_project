const express = require('express');
const passport = require('passport');
const bcrypt = require('bcrypt');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');
const way = require('../models/way');
const wayInfo = require('../models/wayInfo');

const router = express.Router();

async function resMsg(result,res){
  return res.json(result);
}

router.post('/searchStationListsByBusLineName', isNotLoggedIn, async (req, res, next) => {
  //const { busLineName } = req.body;
  const busLineName = "4312";
    try {    
        const busLineNames = await wayInfo.findAll({ where: { 노선명 : busLineName } });
        const result = {};
        if (busLineNames) {
          result["busLineNames"] = busLineNames;
          console.log(busLineNames);
          result["success"] = "100";
          result["msg"] = "getLists successed";
          await resMsg(result,res);
          return false;
        }
        result["busLineNames"] = "";
        result["success"] = "100";
        result["msg"] = "getLists failed";
        
        await resMsg(result,res);
    } catch (error) {
        console.error(error);
        return next(error);
    }
});


module.exports = router;