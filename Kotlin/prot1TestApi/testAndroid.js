const express = require('express');

const app = express();
const bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({extended:true}));


app.post("/login",(req,res)=>{
    console.log("/login 요청 왔음");
    const userid = req.body["userid"];
    const userpw = req.body["userpw"];
    //db코드로 바꿔줘야함
    const result = {}

    if (userid == "aaaa" && userpw == "1234"){
        console.log("아이디 패스워드 일치");
        result["userid"] = "aaaa";
        result["userpw"] = "1234";
        result["success"] = "200";
        result["msg"] = "login success";
        res.json(result);
    }
    //db코드로 바꿔줘야함
})

app.listen(8080,(req,res)=>{
    console.log('서버 대기중...');
})