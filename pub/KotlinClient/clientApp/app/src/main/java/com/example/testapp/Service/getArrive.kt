package com.example.testapp.Service

import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.KeyModel.ApiKeyTwo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface getArrive {
    // 노선 id를 입력하면 노선id랑 정류소 id 랑 또 순번?으로 정류소에 첫번쨰로 도착하는 bus의 id를 가져옴
    // 4312 와 노선 id 정류소 id와 순번을 입력하면 그 정류소에 첫번째로 도착하는 bus의 id를 가져온다
    // (2) getArrInfoByRouteList (버스도착예정)
    @GET("arrive/getArrInfoByRoute?serviceKey=" + ApiKeyTwo.API_KEY)
    fun getArrive(
        @Query("stId") stId:String,
        @Query("busRouteId") busRouteId: String,
        @Query("ord") ord:String, // 정류소 순번
        @Query("resultType") resultType:String
    ): Call<Bus>
}

