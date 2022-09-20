package com.example.testapp.Service

import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyOne
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface getStation {
    //노선 id로 정류소 목록을 가져온다. 정류소가 쭉 나와서? 그것을 for문으로 그것들만 모아서 나오게 한거다?
    @GET("busRouteInfo/getStaionByRoute?serviceKey=" + ApiKeyOne.API_KEY)
    fun getStation(
        @Query("busRouteId") busRouteId:String,
        @Query("resultType") resultType:String
    ): Call<Bus>
}