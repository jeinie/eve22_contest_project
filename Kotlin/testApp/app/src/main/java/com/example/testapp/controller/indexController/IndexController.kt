package com.example.testapp.controller.indexController

import android.util.Log
import com.example.testapp.Model.indexModel.UserInfo
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class IndexController {

    fun getLogin(userid : String,userpw : String){
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080").
        addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(LoginService::class.java)

        service.getUser(userid,userpw,"","")?.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful){
                    var result: UserInfo? = response.body()
                    Log.d("YMC", "onResponse1 성공: " + result?.getUserid());
                    Log.d("YMC", "onResponse1 성공: " + result?.getUserpw());
                    Log.d("YMC", "onResponse1 성공: " + result?.getSuccess());
                    Log.d("YMC", "onResponse1 성공: " + result?.getMsg());

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