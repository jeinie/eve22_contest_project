package com.example.myapplication.Service

import com.example.myapplication.PayInfo.PayInfo
import retrofit2.Call
import retrofit2.http.*

interface tossPay {
    @POST("https://api.tosspayments.com/v1/billing/authorizations/test_ck_mnRQoOaPz8LWNBzJXj5ry47BMw6v")
    fun getContent(
        @Header("authorization") authorization: String,
        @Field("Content-Type") contentType: String
    )

    @GET("/pay")
    fun payBus(
        @Query("amount") amount: Int,
        @Query("passengerId") passengerId: String
    ): Call<PayInfo>
}