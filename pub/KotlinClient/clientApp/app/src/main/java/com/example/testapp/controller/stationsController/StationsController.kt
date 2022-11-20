package com.example.testapp.controller.stationsController

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testapp.Model.ArriveBusInfo.ArriveBusInfo
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyTwo
import com.example.testapp.Service.getArrive
import com.example.testapp.activity.BusesAtStationAcitivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StationsController {
    fun getArriveBus(stId : String , routeId : String, ord : String,routeNum : String, stationNm : String , applicontext : Context){
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyTwo.DOMAIN).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getArrive::class.java)
        service.getArrive(stId,routeId,ord,"json").enqueue(object : Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful) {
                    if(response.body()?.msgBody?.itemList != null){

                        var arriveBusList = ArrayList<ArriveBusInfo>()
                        //첫번째 버스 넣어줌
                        arriveBusList.add(ArriveBusInfo(response.body()!!.msgBody.itemList[0].arrmsg1,response.body()!!.msgBody.itemList[0].vehId1,routeNum))
                        //두번째 버스 넣어줌
                        arriveBusList.add(ArriveBusInfo(response.body()!!.msgBody.itemList[0].arrmsg2,response.body()!!.msgBody.itemList[0].vehId2,routeNum))
                        var intent = Intent(applicontext, BusesAtStationAcitivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("stId",stId)
                        intent.putExtra("routeId",routeId)
                        intent.putExtra("ord",ord)
                        intent.putExtra("routeNum",routeNum)
                        intent.putExtra("stationNm",stationNm)
                        intent.putExtra("ArriveBusesInfoList",arriveBusList)
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

