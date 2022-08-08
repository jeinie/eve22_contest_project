package com.example.testapp.Service

import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyThree
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface getBusPos {
    @GET("buspos/getBusPosByVehId?serviceKey=" + ApiKeyThree.API_KEY)
    fun getBusPos(
        @Query("vehId") vehId:String,
        @Query("resultType") resultType:String
    ): Call<Bus>
}