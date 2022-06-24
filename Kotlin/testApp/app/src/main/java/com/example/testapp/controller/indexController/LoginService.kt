package com.example.testapp.controller.indexController

import com.example.testapp.Model.indexModel.UserInfo
import retrofit2.Call
import retrofit2.http.*


interface LoginService {
    //GET 예제

    @FormUrlEncoded
    @POST("/login")
    fun getUser(
        @Field("userid") userid: String,
        @Field("userpw") userpw: String,
        @Field("success") success: String,
        @Field("msg") msg: String,
    ): Call<UserInfo>

}