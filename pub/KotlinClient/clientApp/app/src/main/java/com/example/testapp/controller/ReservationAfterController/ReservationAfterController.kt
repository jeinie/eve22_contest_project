package com.example.testapp.controller.ReservationAfterController

import android.util.Log
import com.example.testapp.Model.BusCoordinateModel.BusCoordinateModel
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyThree
import com.example.testapp.Service.getBusPos
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReservationAfterController {
    //그냥 변화되는 포지션만 지도에 꽂아줘야하니 포지션만 지속적으로 알아와야한다
    fun getBusPositionRepeat( vehId : String ) : BusCoordinateModel {
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyThree.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getBusPos::class.java)
        var call : Call<Bus> = service.getBusPos(vehId,"json")
        var body = call.execute().body()
        Log.d("카페라떼마싯넹 확인","tmx : ${body!!.msgBody.itemList[0].tmX} , tmy : ${body!!.msgBody.itemList[0].tmY}")
        Log.d("뭐뭐왔었더라", "뭐뭐왔었더라 : ${body!!.msgBody.itemList[0]}")

        var busCoordinateModel = BusCoordinateModel(body!!.msgBody.itemList[0].tmX.toDouble(),body!!.msgBody.itemList[0].tmY.toDouble())
        return busCoordinateModel
    }
}