const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const payRouter = require("./routes/pay");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use("/", payRouter);

app.listen(3030, () => {
  console.log("3030번 포트에서 대기중...");
});
