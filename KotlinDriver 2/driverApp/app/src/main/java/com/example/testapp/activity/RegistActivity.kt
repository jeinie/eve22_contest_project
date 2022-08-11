package com.example.testapp.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.controller.registController.RegistController
import com.example.testapp.databinding.ActivityRegistBinding

open class RegistActivity : AppCompatActivity(){
    //L = Login
    //R = Regist
    //UID = UserId
    //UPW = UserPw
    //TF = TextField
    //BTN = button
    lateinit var binding : ActivityRegistBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#FFFFFF")
        binding = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //가입 버튼
        var R_BTN = binding.RegistRegistBtn

        R_BTN.setOnClickListener { view ->
            Log.d("1RegistActivity","1들어오는지 확인")
            //cardnum과 busId 는 나중에 다른곳에서 추가될것.

            var id = binding.RegistUserId.text.toString()
            var password = binding.RegistUserPw.text.toString()
            var nick = binding.RegistUserNick.text.toString()
            var name = binding.RegistUserName.text.toString()



            Log.d("RegistActivity","들어오는지 확인")
            var rcon = RegistController()
            rcon.getRegist(id,password,nick,name,applicationContext)

        }

    }
}