const express = require("express");
const router = express.Router();
const uuid = require("uuid").v4;
const testkey = "test_ck_mnRQoOaPz8LWNBzJXj5ry47BMw6v";

router.get('/success', (req, res) => {
    console.log(`보내온 내용 : ${req.query.amount}, ${req.query.passengerId}`);
    const data = {
        amount: req.query.amount,
        passengerId: req.query.passengerId,
        orderId: uuid()
    }
    res.send(data);
})

router.get('/pay', (req, res) => {
    got.post("https://api.tosspayments.com/v1/payments/" + req.query.paymentkey, {
        headers: {
            Authorization: "Basic" + Buffer.from(testkey + ":").toString("base64"),
            "Content-Type": "application/json",
        },
        json: {
            orderId: req.query.orderId,
            amount: req.query.amount
        }
    })
    .then(function(response) {
        console.log(response.body);
    })
})

module.exports = router;