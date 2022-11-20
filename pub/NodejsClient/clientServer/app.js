const express = require("express");
const cookieParser = require("cookie-parser");
const path = require("path");
const session = require("express-session");
const dotenv = require("dotenv");
const passport = require("passport");

//post떄문에
const qs = require("qs");

dotenv.config();

const auth_C_Router = require("./routes/auth_C");
const pageRouter = require("./routes/page");
const locationRouter = require("./routes/location");
const stationRouter = require("./routes/station");

const { sequelize } = require("./models");

const passportConfig = require("./passport");
const bodyParser = require("body-parser");

/**/
/*
const admin = require("firebase-admin");
const firestore = require("firebase-admin/firestore");
const serviceAccount = require("./hscoin-d8ff7-firebase-adminsdk-unmpe-a6a77a60b5.json");
const firebaseConfig = {
  apiKey: "AIzaSyAHUg25ak_qTeKbHathmfnMuey4UeJTrkQ",
  authDomain: "hscoin-d8ff7.firebaseapp.com",
  projectId: "hscoin-d8ff7",
  storageBucket: "hscoin-d8ff7.appspot.com",
  messagingSenderId: "928676142936",
  appId: "1:928676142936:web:72e7970feb2b29c792cf2d",
  measurementId: "G-86FLCDRTND",
  credential: admin.credential.cert(serviceAccount),
};
*/

// Import the functions you need from the SDKs you need
const { initializeApp } = require("firebase/app");
//const { getAnalytics } = require("firebase/analytics");
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAHUg25ak_qTeKbHathmfnMuey4UeJTrkQ",
  authDomain: "hscoin-d8ff7.firebaseapp.com",
  databaseURL: "https://hscoin-d8ff7-default-rtdb.firebaseio.com",
  projectId: "hscoin-d8ff7",
  storageBucket: "hscoin-d8ff7.appspot.com",
  messagingSenderId: "928676142936",
  appId: "1:928676142936:web:72e7970feb2b29c792cf2d",
  measurementId: "G-86FLCDRTND",
};

// Initialize Firebase
const fbapp = initializeApp(firebaseConfig);
//const analytics = getAnalytics(fbapp);

const { Storage } = require("@google-cloud/storage");

//global.firebaseAdmin = admin.initializeApp(firebaseConfig);
/*
async function initDB() {
  global.db = firestore.getFirestore();
  global.storage = await global.firebaseAdmin.storage();
}
*/
/**/

const app = express();

passportConfig(); // 패스포트 설정

app.set("port", process.env.PORT || 8080);
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
app.use("/auth_C", auth_C_Router);
app.use("/location", locationRouter);
app.use("/station", stationRouter);

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

app.get("/getTrueRDB", (req, res) => {
  firebase.database().ref("approved_users/").update({ test: "data" });
  firebase.database().ref("approved_users/").push({
    test: "data",
  });
});

app.get("/getFalseRDB", (req, res) => {});

app.listen(app.get("port"), () => {
  console.log(app.get("port"), "번 포트에서 대기중");
});
