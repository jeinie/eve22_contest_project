package com.example.testapp.controller.indexController

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.testapp.Model.UserModel.UserInfo
import com.example.testapp.Service.LoginService
import com.example.testapp.activity.IndexActivity
import com.example.testapp.activity.MainActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class IndexController {

    fun getLogin(id : String,password : String,applicontext : Context){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3000").
        addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(LoginService::class.java)

        service.getUser(id,password,"","",0,"","")?.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful){
                    var result: UserInfo? = response.body()
                    Log.d("Login", "onResponse1 성공: " + result?.getUserid());
                    Log.d("Login", "onResponse1 성공: " + result?.getUserpw());
                    Log.d("Login", "onResponse1 성공: " + result?.getUserNick());
                    Log.d("Login", "onResponse1 성공: " + result?.getUserName());
                    Log.d("Login", "onResponse1 성공: " + result?.getUserAge());
                    Log.d("Login", "onResponse1 성공: " + result?.getSuccess());
                    Log.d("Login", "onResponse1 성공: " + result?.getMsg());

                    //로그인 실패일때
                    if (result?.getSuccess() == "100") {
                        Toast.makeText(applicontext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        Log.d("로그인", "실패")

                    } else if (result?.getSuccess() == "200") {
                        //로그인 성공일때
                        Toast.makeText(applicontext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        Log.d("로그인", "성공")
                        //로그인을 성공했으면 이제 사용자 화면으로 가야함
                        var intent = Intent(applicontext, MainActivity::class.java)

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        applicontext.startActivity(intent)
                        //인덱스 페이지로 돌아가고 현재 regist페이지는 stack에 쌓이지 않고 종료되게 만든다
                    }


                }else{
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