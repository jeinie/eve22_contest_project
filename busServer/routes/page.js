const express = require('express');
const router = express.Router();
let registerNum;
var js = {};



var js2 = {};





router.get('/passenger', (req, res) => {
    console.log("passenger 요청 들어옴");
    console.log("버스아이디: " + req.query.vehId);
    console.log("정류장: " + req.query.stNm);

    console.log('타입 : ' + typeof(js[req.query.stNm]));
    if ( js[req.query.stNm] == undefined ){
        js[req.query.stNm] = 0;
        js[req.query.stNm] = js[req.query.stNm] + 1;    
        js2[req.query.vehId] = js;
    }else {
        js[req.query.stNm] = js[req.query.stNm] + 1;    
        js2[req.query.vehId] = js;
    }
    //registerNum = req.query.registerNum; // 현재 예약 인원을 registerNum에 저장해서 driver.js에서 사용?
    console.log(js2);
    res.json("확인");
});

router.get('/driver', (req, res) => {
    console.log('응답 보내기');
    //console.log(registerNum);
    req.body.registerNum1 = registerNum;
    console.log(req.body.registerNum1);
    res.json(req.body);
});

module.exports = router;