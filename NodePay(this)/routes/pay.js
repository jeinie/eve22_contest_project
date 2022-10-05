// 빌링키 발급받기
// 카드번호, 카드 유효기간, 카드소유자의 생년월일, 카드 비밀번호(선택)를 빌링키 발급 요청 API 본문에 포함
// 이 카드 정보는 빌링키 발급할 때만 한 번 사용됨. 이후에는 발급받은 빌링키를 자동 결제 수단으로 사용
// customerKey: 가맹점에서 고객을 특정하는 값. (요청본문에 포함해야 함)

const { response } = require("express");
const express = require("express");
const router = express.Router();

var axios = require("axios").default;

router.get("/pay", (req, res) => {
  /*var axios = require("axios").default;
  var options = {
    method: 'POST',
    // 2. /v1/billing/ 이 뒤부터는 발급받은 빌링키 (이걸 Path 파라미터로 포함시키면 결제 승인 요청을 하는 것)
    url: 'https://api.tosspayments.com/v1/billing/rG5uMLcqr0kuDcqMkeUh0-tVlJq9QMnRlmYtRest2ug=',
    headers: {
      Authorization: 'Basic dGVzdF9za19QMjR4TGVhNXpWQU5KWGxsYXl4clFBTVlOd1c2Og==',
      'Content-Type': 'application/json'
    },
    data: {
      cardExpirationYear: '24',
      cardExpirationMonth: '10',
      cardPassword: '00',
      customerIdentityNumber: '881212',
      customerKey: 'iFjPYpKhmKecXBAegsSrV',
      amount: req.query.amount,
      orderName: '버스요금',
      orderId: 'lbr_c4WScR-HRSQjEvieo',
      customerName: req.query.passengerId,
      // 카드번호 추가
      cardNumber: '6233745026535027'
    }
  };
  // 3. 요청이 승인되면 빌링키를 발급받을 때 등록했던 카드로 결제됨. card 객체가 포함된 응답이 돌아옴

  // 1. 발급된 빌링키가 포함된 응답이 돌아옴 ("billingKey": "...")
  axios.request(options).then(function (response) {
    console.log(response.data);
  }).catch(function (error) {
    console.error(error);
  });

  // 결제 완료되었다는 내용의 객체
  // true이면 결제 성공, false이면 결제 실패 (일단 임시로)
  const result = {
    pay: "true"
  }
  
  res.send({"pay": "true"});*/


  // 빌링키 발급받는 방법은 사업자 등록을 해야 사용할 수 있는 것 같음.
  // 그래서 위 방법 말고 아래 방법으로 카드 번호 결제 API 사용으로 결정
  var options = {
    method: 'POST',
    url: 'https://api.tosspayments.com/v1/payments/key-in',
    headers: {
      Authorization: 'Basic dGVzdF9za19QMjR4TGVhNXpWQU5KWGxsYXl4clFBTVlOd1c2Og==',
      'Content-Type': 'application/json'
    },
    data: {
      amount: req.query.amount,
      // orderId를 한 번 사용하면 다시 재사용이 안됨... 새로 만들어서 해야 하는 것 같음
      // 이건 그냥 사용자 아이디로 앱에서 보내주는 게 나을 듯
      orderId: 'gXqf9N8mDmd6ozEoA4YTI3',
      orderName: req.query.passengerId,
      cardNumber: req.query.cardNumber,
      cardExpirationYear: req.query.cardExpirationYear,
      cardExpirationMonth: req.query.cardExpirationMonth,
      cardPassword: req.query.cardPassword,
      //customerIdentityNumber: '881212'
    }
  };

  axios.request(options).then(function (response) {
    console.log(response.data);
  }).catch(function (error) {
    console.error(error);
  });

  res.json(result);
})

module.exports = router;
