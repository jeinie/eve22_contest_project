package com.example.buspassenger.Service

import com.example.buspassenger.Model.RegisterInfo
import retrofit2.Call
import retrofit2.http.*

interface BusPassengersInfo {
    //@FormUrlEncoded
    @GET("/passenger")
    fun getBusStatus(
        // 예약 인원
        //@Field("registerNum") registerNum: Int
        @Query("vehId") vehId: String,
        @Query("stNm") stNm: String
    ): Call<RegisterInfo>
}