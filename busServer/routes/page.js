const express = require("express");
const router = express.Router();
let registerNum;

var jsonStatus = {};
router.get("/getReservation", (req, res) => {
  console.log("passenger 요청 들어옴");
  console.log("버스아이디: " + req.query.vehId);
  console.log("정류장: " + req.query.stNm);
  jsonStatus[`${req.query.vehId + "," + req.query.stNm}`] =
    jsonStatus[`${req.query.vehId + "," + req.query.stNm}`] == undefined
      ? (jsonStatus[`${req.query.vehId + "," + req.query.stNm}`] = 1)
      : jsonStatus[`${req.query.vehId + "," + req.query.stNm}`] + 1;

  console.log(jsonStatus);
  const data = {
    registerNum: 0,
    passengerName: "",
    driverName: "",
  };

  res.json(data);
});

// 예약 취소 버튼 (맞는지 확인)
router.get("/cancelReservation", (req, res) => {
  console.log("passenger/cancel 요청 들어옴");
  console.log("버스아이디: " + req.query.vehId);
  console.log("정류장: " + req.query.stNm);
  jsonStatus[`${req.query.vehId + "," + req.query.stNm}`] -= 1;
  console.log(jsonStatus);

  const data = {
    registerNum: 0,
    passengerName: "",
    driverName: "",
  };

  res.json(data);
});

module.exports = router;
