//const express = require("express");
//const router = express.Router();

var axios = require("axios").default;
var options = {
  method: 'POST',
  url: 'https://api.tosspayments.com/v1/billing/authorizations/card',
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
    customerName: "승객 ID 또는 승객 이름"
  }
};

axios.request(options).then(function (response) {
  console.log(response.data);
}).catch(function (error) {
  console.error(error);
});

/*router.get("/pay", (req, res) => {
  console.log(`버스요금: ${req.query.amount}, 승객ID: ${req.query.passengerId}`)
})

module.exports = router;*/