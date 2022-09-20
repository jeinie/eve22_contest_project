package com.example.testapp.Service

import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyOne
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface getBus {
    // busRouteInfo/getBusRouteList?serviceKey=[서비스키]&strSrch=6003&resultType=json
    // (노선 번호)4312를 입력하면 노선 id(10101011001)를 가지고오는
    @GET("busRouteInfo/getBusRouteList?serviceKey=" + ApiKeyOne.API_KEY)
    fun getBus(
        @Query("strSrch") busNum:String,
        @Query("resultType") resultType:String
    ): Call<Bus>
}