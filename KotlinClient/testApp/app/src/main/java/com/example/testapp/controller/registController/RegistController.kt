package com.example.testapp.controller.registController

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.testapp.Model.UserModel.UserInfo
import com.example.testapp.Service.RegistService
import com.example.testapp.activity.IndexActivity
import com.example.testapp.activity.RegistActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegistController{

    fun getRegist(
            id: String,
            password: String,
            nick: String,
            name: String,
            age: Int,
            applicontext : Context
    ){
        val retrofit = Retrofit.Builder().baseUrl("http://54.193.29.150:8080").
        addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(RegistService::class.java)

        service.RegistUser(id, password, nick, name, age, "", "")?.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    var result: UserInfo? = response.body()

                    Log.d("RegistController_getRegist", "onResponse1 성공: " + result?.getSuccess());
                    Log.d("RegistController_getRegist", "onResponse1 성공: " + result?.getMsg());
                    //회원가입 실패일때
                    if (result?.getSuccess() == "100") {
                        Toast.makeText(applicontext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        Log.d("회원가입", "실패")

                    } else if (result?.getSuccess() == "200") {
                        //회원가입 성공일때
                        Toast.makeText(applicontext, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        Log.d("회원가입", "성공")
                        var intent = Intent(applicontext, IndexActivity::class.java)
                        applicontext.startActivity(intent)
                        //인덱스 페이지로 돌아가고 현재 regist페이지는 stack에 쌓이지 않고 종료되게 만든다
                    }

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }

        })
    }

}