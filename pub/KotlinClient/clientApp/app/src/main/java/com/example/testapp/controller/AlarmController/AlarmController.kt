package com.example.testapp.controller.AlarmController

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testapp.Model.ArriveBusInfo.ArriveBusInfo
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyTwo
import com.example.testapp.Service.getAlarmArriveCheck
import com.example.testapp.Service.getArrive
import com.example.testapp.activity.BusesAtStationAcitivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AlarmController {

    fun getAlarm(stId : String , routeId : String, ord : String,routeNum : String, stationNm : String , applicontext : Context){
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyTwo.DOMAIN).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(getAlarmArriveCheck::class.java)
        var call: Call<Bus> = service.getAlarmArriveCheck(stId,routeId,ord,"json")
        var body = call.execute().body()

        try{

        }catch( e : Exception ) {

        }

        var arriveBusList = ArrayList<ArriveBusInfo>()
        //첫번째 버스 넣어줌
        //arriveBusList.add(ArriveBusInfo(response.body()!!.msgBody.itemList[0].arrmsg1,response.body()!!.msgBody.itemList[0].vehId1,routeNum))
        //두번째 버스 넣어줌
        //arriveBusList.add(ArriveBusInfo(response.body()!!.msgBody.itemList[0].arrmsg2,response.body()!!.msgBody.itemList[0].vehId2,routeNum))
        /*
                            stId
                            routeId
                            ord
                            routeNum
                            stationNm
                            arriveBusList
                        */

    }

}