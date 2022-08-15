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

    var checkVehId = js2.hasOwnProperty(req.query.vehId);
    console.log(`vehId 있는지 확인 ${checkVehId}`);
    
    if (checkVehId == false) { // vehId가 없으면

        /*if ( js[req.query.stNm] == undefined ){*/
            // 해당 vehId가 현재 없으면 새로 만들어서 추가
            //console.log(`현재 예약 인원 : ${js2[req.query.stNm]}`);
            js = {};
            js[req.query.stNm] = 0;
            js[req.query.stNm] = js[req.query.stNm] + 1;
            js2[req.query.vehId] = js; 
            console.log(`지금 json은 어떻게? : ${JSON.stringify(js2)}`);     
        
    } else { // vehId가 이미 존재하면
        // 현재 js2에 없는 다른 정류장에서 예약을 하면 undefined 이므로 확인해야함
        if ( js[req.query.stNm] == undefined ) {
            js[req.query.stNm] = 0;
            js[req.query.stNm] = js[req.query.stNm] + 1;
            js2[req.query.vehId] = js; 
        } else {
            js[req.query.stNm] = js[req.query.stNm] + 1;
            js2[req.query.vehId] = js;
        }
        
        console.log(`현재 예약 인원 : ${JSON.stringify(js2[req.query.vehId])}`);
    }

    console.log(js2);
    res.json("확인");
});

// 예약 취소 버튼 (맞는지 확인)
router.get('/passenger/cancel', (req, res) => {
    console.log("passenger/cancel 요청 들어옴");
    console.log("버스아이디: " + req.query.vehId);
    console.log("정류장: " + req.query.stNm);
    console.log('타입 : ' + typeof(js[req.query.stNm]));

    js[req.query.stNm] = js[req.query.stNm] - 1;
    js2[req.query.vehId] = js;
})

/*router.get('/driver', (req, res) => {
    console.log('응답 보내기');
    res.json(req.body);
});*/

module.exports = router;