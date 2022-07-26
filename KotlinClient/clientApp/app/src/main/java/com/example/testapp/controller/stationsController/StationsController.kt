package com.example.testapp.controller.stationsController

import android.content.Context
import android.util.Log
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyTwo
import com.example.testapp.Service.getArrive
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StationsController {
    fun getArriveBus(stId : String , routeId : String, ord : String, applicontext : Context){
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyTwo.DOMAIN).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getArrive::class.java)
        print("오긴 하나요?")
        Log.d("확인","오긴 하나요?")
        Log.d("대체 무슨일 ","stId : ${stId} \n routeId: ${routeId} \nord: ${ord}")
        service.getArrive(stId,routeId,ord,"json").enqueue(object : Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                Log.d("D","response body 뭐라오길래? ${response.body()}")
                Log.d("D","response message 뭐라오길래? ${response.message()}")
                if (response.isSuccessful) {
                    if(response.body()?.msgBody?.itemList != null){
                    Log.d("경계 위 ","위에 위에 안오나요? ${response.body()}")
                    //if(response.body()?.serviceresult?.msgbody?.itemlist != null){
                        print("${response.body()}")
                        Log.d("경계 위 ","위에 안오나요? ${response.body()}")
                        print("안오나요? 위 ${response.body()}")
                        //var arriveBusList : ArrayList<>


                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.d("경계 밑 ","위에 안오나요? ")
                print("안오나요? 밑 ")
            }
        })

    }
}