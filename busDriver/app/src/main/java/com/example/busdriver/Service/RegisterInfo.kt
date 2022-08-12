package com.example.busdriver.Service

import com.example.busdriver.Model.RegisterInfo_D
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RegisterInfo {
    @GET("/driver")
    fun getRegisterNum(
        //@Query("registerNum1") registerNum1 : Int
    ):Call<RegisterInfo_D>
}