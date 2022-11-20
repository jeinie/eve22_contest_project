const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const pageRouter = require("./routes/page");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use("/", pageRouter);

app.listen(3000, () => {
  console.log("3000번 포트에서 대기중...");
});
