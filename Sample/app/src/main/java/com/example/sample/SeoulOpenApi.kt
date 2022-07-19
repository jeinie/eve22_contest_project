package com.example.sample

import com.example.sample.data.Bus
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

object SeoulOpenApi {
    val DOMAIN = "http://ws.bus.go.kr/api/rest/" // 공통적인 부분
    const val API_KEY = "39ekznXIYt%2BJ64ptAzOuc%2FAGITrE4ju50rRkR6mc1%2FlsgwNln9%2Bl33LiEPxKA5lQduSuD2ZTAHRBH7WGXyCH%2FQ%3D%3D"
}

interface SeoulOpenService {
    // busRouteInfo/getBusRouteList?serviceKey=[서비스키]&strSrch=6003&resultType=json
    // (노선 번호)4312를 입력하면 노선 id(10101011001)를 가지고오는
    @GET("busRouteInfo/getBusRouteList?serviceKey=" + SeoulOpenApi.API_KEY)
    fun getBus(
        @Query("strSrch") busNum:String,
        @Query("resultType") resultType:String
    ):Call<Bus>

    //노선 id로 정류소 목록을 가져온다. 정류소가 쭉 나와서? 그것을 for문으로 그것들만 모아서 나오게 한거다?
    @GET("busRouteInfo/getStaionByRoute?serviceKey=" + SeoulOpenApi.API_KEY)
    fun getStation(
        @Query("busRouteId") busRouteId:String,
        @Query("resultType") resultType:String
    ):Call<Bus>

    // 노선 id를 입력하면 노선id랑 정류소 id 랑 또 순번?으로 정류소에 첫번쨰로 도착하는 bus의 id를 가져옴
    // 4312 와 노선 id 정류소 id와 순번을 입력하면 그 정류소에 첫번째로 도착하는 bus의 id를 가져온다
    // (2) getArrInfoByRouteLis (버스도착예정)
    @GET("arrive/getArrInfoByRoute?=serviceKey=" + SeoulOpenApi.API_KEY)
    fun getArrive(
        @Query("stId") stId:String,
        @Query("busRouteId") busRouteId: String,
        @Query("ord") ord:String, // 정류소 순번
        @Query("resultType") resultType:String
    ):Call<Bus>
}