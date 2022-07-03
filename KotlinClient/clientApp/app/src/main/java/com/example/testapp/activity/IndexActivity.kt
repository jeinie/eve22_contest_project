package com.example.testapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testapp.controller.indexController.IndexController
import com.example.testapp.databinding.ActivityIndexBinding

//L = Login
//R = Regist
//UID = UserId
//UPW = UserPw
//TF = TextField
//BTN = button


class IndexActivity : AppCompatActivity() {
    //손님용 앱
    lateinit var binding : ActivityIndexBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIndexBinding.inflate(layoutInflater)

        setContentView(binding.root)



        //로그인쪽 유저 아이디 입력칸
        var L_UID_TF = binding.LoginUserId
            //findViewById<EditText>(R.id.Login_UserId)
        //로그인쪽 유저 패스워드 입력칸
        var L_UPW_TF = binding.LoginUserPw
            //findViewById<EditText>(R.id.Login_UserPw)

        var L_BTN = binding.LoginLoginBtn
            //findViewById<Button>(R.id.Login_LoginBtn)

        var R_BTN = binding.LoginRegistBtn
        
        //로그인하는 버튼 이벤트
        L_BTN.setOnClickListener{ view ->
            var icon = IndexController()
            icon.getLogin(L_UID_TF.text.toString(),L_UPW_TF.text.toString(),applicationContext)
        }
        //회원가입페이지로가는 버튼 이벤트
        R_BTN.setOnClickListener { view ->
            var intent = Intent(baseContext, RegistActivity::class.java)
            startActivity(intent)
        }

    }
}