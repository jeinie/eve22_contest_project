// 빌링키 발급받기
// 카드번호, 카드 유효기간, 카드소유자의 생년월일, 카드 비밀번호(선택)를 빌링키 발급 요청 API 본문에 포함
// 이 카드 정보는 빌링키 발급할 때만 한 번 사용됨. 이후에는 발급받은 빌링키를 자동 결제 수단으로 사용
// customerKey: 가맹점에서 고객을 특정하는 값. (요청본문에 포함해야 함)

var axios = require("axios").default;
var options = {
  method: 'POST',
  // 2. /v1/billing/ 이 뒤부터는 발급받은 빌링키 (이걸 Path 파라미터로 포함시키면 결제 승인 요청을 하는 것)
  url: 'https://api.tosspayments.com/v1/billing/rG5uMLcqr0kuDcqMkeUh0-tVlJq9QMnRlmYtRest2ug=',
  headers: {
    Authorization: 'Basic dGVzdF9za19QMjR4TGVhNXpWQU5KWGxsYXl4clFBTVlOd1c2Og==',
    'Content-Type': 'application/json'
  },
  data: {
    cardNumber: '5389038111615696',
    cardExpirationYear: '24',
    cardExpirationMonth: '10',
    cardPassword: '17',
    customerIdentityNumber: '881212',
    customerKey: 'iFjPYpKhmKecXBAegsSrV',
    amount: 1250,
    orderName: '버스요금',
    orderId: '5TlGRwn1D1QAU0Y6DSQyX',
    customerName: "승객 ID 또는 승객 이름"
  }
};
// 3. 요청이 승인되면 빌링키를 발급받을 때 등록했던 카드로 결제됨. card 객체가 포함된 응답이 돌아옴

// 1. 발급된 빌링키가 포함된 응답이 돌아옴 ("billingKey": "...")
axios.request(options).then(function (response) {
  console.log(response.data);
}).catch(function (error) {
  console.error(error);
});

/*router.get("/pay", (req, res) => {
  console.log(`버스요금: ${req.query.amount}, 승객ID: ${req.query.passengerId}`)
})
*/