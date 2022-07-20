package com.example.testapp

import com.example.testapp.data.Bus
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

object SeoulOpenApi {
    val DOMAIN = "http://ws.bus.go.kr/api/rest/" // 공통적인 부분
    const val API_KEY = "39ekznXIYt%2BJ64ptAzOuc%2FAGITrE4ju50rRkR6mc1%2FlsgwNln9%2Bl33LiEPxKA5lQduSuD2ZTAHRBH7WGXyCH%2FQ%3D%3D"
}

interface SeoulOpenService {
    // busRouteInfo/getBusRouteList?serviceKey=[서비스키]&strSrch=6003&resultType=json
    @GET("busRouteInfo/getBusRouteList?serviceKey=" + SeoulOpenApi.API_KEY)
    fun getBus(
        @Query("strSrch") busNum:String,
        @Query("resultType") resultType:String
    ):Call<Bus>

    //
    @GET("busRouteInfo/getStaionByRoute?serviceKey=" + SeoulOpenApi.API_KEY)
    fun getStation(
        @Query("busRouteId") busRouteId:String,
        @Query("resultType") resultType:String
    ):Call<Bus>

    // (2) getArrInfoByRouteLis (버스도착예정)
    @GET("arrive/getArrInfoByRoute?=serviceKey=" + SeoulOpenApi.API_KEY)
    fun getArrive(
        @Query("stId") stId:String,
        @Query("busRouteId") busRouteId: String,
        @Query("ord") ord:String, // 정류소 순번
        @Query("resultType") resultType:String
    ):Call<Bus>
}