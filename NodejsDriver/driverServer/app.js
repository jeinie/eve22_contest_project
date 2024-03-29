const express = require("express");
const cookieParser = require("cookie-parser");
const path = require("path");
const session = require("express-session");
const dotenv = require("dotenv");
const passport = require("passport");

//post떄문에
const qs = require("qs");

dotenv.config();

const auth_D_Router = require("./routes/auth_D");
const pageRouter = require("./routes/page");
const locationRouter = require("./routes/location");

const { sequelize } = require("./models");

const passportConfig = require("./passport");
const bodyParser = require("body-parser");

const app = express();

passportConfig(); // 패스포트 설정

app.set("port", process.env.PORT || 3001);
app.set("view engine", "ejs");
app.set("views", __dirname + "/views");

sequelize
  .sync({ force: false })
  .then(() => {
    console.log("데이터베이스 연결 성공");
  })
  .catch((err) => {
    console.error(err);
  });

app.use(express.static(path.join(__dirname, "public")));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(cookieParser(process.env.COOKIE_SECRET));
app.use(
  session({
    resave: false,
    saveUninitialized: false,
    secret: process.env.COOKIE_SECRET,
    cookie: {
      httpOnly: true,
      secure: false,
    },
  })
);

app.use(passport.initialize());
app.use(passport.session());

//라우터 세팅
app.use("/", pageRouter);
app.use("/auth_D", auth_D_Router);
app.use("/location", locationRouter);

app.use((req, res, next) => {
  const error = new Error(`${req.method} ${req.url} 라우터가 없습니다.`);
  error.status = 404;
  next(error);
});

app.use((err, req, res, next) => {
  res.locals.message = err.message;
  res.locals.error = process.env.NODE_ENV !== "production" ? err : {};
  res.status(err.status || 500);
  res.render("error");
});

app.listen(app.get("port"), () => {
  console.log(app.get("port"), "번 포트에서 대기중");
});
