package com.example.testapp.Service

import com.example.buspassenger.Model.RegisterInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface cancelReservation {
    @GET("/cancelReservation")
    fun cancelBusReservation(
        // 예약 인원
        //@Field("registerNum") registerNum: Int
        @Query("vehId") vehId: String,
        @Query("stNm") stNm: String
    ): Call<RegisterInfo>
}