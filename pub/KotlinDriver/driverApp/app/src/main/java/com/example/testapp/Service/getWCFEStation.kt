package com.example.testapp.Service

import com.example.testapp.Model.WaitingCntForEachStationModel.WaitingCntForEachStation
import retrofit2.Call
import retrofit2.http.*

interface getWCFEStation {
    //GET 예제
    @FormUrlEncoded
    @POST("/wcfeStation")
    fun getWCFES(
        @Field("vehId") id: String,
        @Field("resultType") resultType: String,
    ): Call<WaitingCntForEachStation>
}

