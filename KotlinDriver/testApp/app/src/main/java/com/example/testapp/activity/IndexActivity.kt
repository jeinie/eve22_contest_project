package com.example.testapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.testapp.Model.indexModel.UserInfo
import com.example.testapp.R
import com.example.testapp.controller.indexController.IndexController

//L = Login
//UID = UserId
//UPW = UserPw
//TF = TextField
//BTN = button

class IndexActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        //로그인쪽 유저 아이디 입력칸
        var L_UID_TF = findViewById<EditText>(R.id.Login_UserId)
        //로그인쪽 유저 패스워드 입력칸
        var L_UPW_TF = findViewById<EditText>(R.id.Login_UserPw)

        var L_BTN = findViewById<Button>(R.id.Login_LoginBtn)

        L_BTN.setOnClickListener{ view ->
            var icon = IndexController()
            icon.getLogin(L_UID_TF.text.toString(),L_UPW_TF.text.toString())
        }

    }
}