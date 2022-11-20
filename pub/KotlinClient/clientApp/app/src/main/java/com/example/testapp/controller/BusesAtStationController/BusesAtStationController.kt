package com.example.testapp.controller.BusesAtStationController

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testapp.Model.ArriveBusInfo.ArriveBusInfo
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyThree
import com.example.testapp.Model.KeyModel.ApiKeyTwo
import com.example.testapp.Service.getArrive
import com.example.testapp.Service.getBusPos
import com.example.testapp.activity.BusesAtStationAcitivity
import com.example.testapp.activity.ReservationAfterActivity
import com.google.android.gms.common.api.internal.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusesAtStationController {
    fun getBusPosition( vehId : String , stName : String , routeNum : String , applicontext : Context ){
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyThree.DOMAIN).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getBusPos::class.java)
        Log.d("대체 무슨일 ","vehId : ${vehId}")
        service.getBusPos(vehId,"json").enqueue(object : Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful) {
                    if(response.body()?.msgBody?.itemList != null){
                        Log.d("버스 좌표 뭐오지 ","버스좌표 안오나요? ${response.body()}")

                        var intent = Intent(applicontext, ReservationAfterActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        intent.putExtra("vehId",vehId)
                        intent.putExtra("stName",stName)
                        intent.putExtra("routeNum",routeNum)
                        intent.putExtra("tmX",response.body()!!.msgBody.itemList[0].tmX)
                        intent.putExtra("tmY",response.body()!!.msgBody.itemList[0].tmY)
                        Log.d("넘기는 정보들은","vehId : ${vehId} \ntmX : ${response.body()!!.msgBody.itemList[0].tmX}\ntmY : ${response.body()!!.msgBody.itemList[0].tmY}")
                        applicontext.startActivity(intent)

                    }
                }
            }
            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("StationsController 의 getArriveBus 에러","getArriveBus retrofit 에러")
            }
        })

    }

}