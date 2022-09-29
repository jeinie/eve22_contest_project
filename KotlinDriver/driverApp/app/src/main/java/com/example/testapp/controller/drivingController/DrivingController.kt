package com.example.testapp.controller.drivingController

import android.util.Log
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyThree
import com.example.testapp.Model.RouteModel.RouteModel
import com.example.testapp.Model.WaitingCntForEachStationModel.WaitingCntForEachStation
import com.example.testapp.Service.getWCFEStation
import com.example.testapp.Service.getBusPos


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DrivingController{

    fun getWaitingCntForEachStation( vehId : String ) :
            WaitingCntForEachStation{
        Log.d("아예 불리지도않아?","안불려?")
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3000").
        addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(getWCFEStation::class.java)
        //
        var call: Call<WaitingCntForEachStation> = service.getWCFES(vehId,"json")
        var body = call.execute().body()
        Log.d("getWaitingCntForEachStation Body뭐라고오는지 : ","${body}")
        return body!!
        //
    }

    fun getBusPos( vehId : String ) : String {

        val retrofit = Retrofit.Builder().baseUrl(ApiKeyThree.DOMAIN).
        addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(getBusPos::class.java)

        var call: Call<Bus> = service.getBusPos(vehId , "json")
        var body = call.execute().body()
        //var routeArrayList = ArrayList<RouteModel>()
        //var lastStnId = body!!.msgBody.itemList[0].lastStnId
        //Log.d("여기가 문제인가 ? ", "${lastStnId}")
        var stId = body!!.msgBody.itemList[0].stId
        Log.d("여기가 문제인가 ? ", "${stId}")
        //return arrayOf(lastStnId,stId)
        return stId
    }


}