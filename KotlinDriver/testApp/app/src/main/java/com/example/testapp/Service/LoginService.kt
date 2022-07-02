package com.example.testapp.Service

import com.example.testapp.Model.UserModel.UserInfo
import retrofit2.Call
import retrofit2.http.*


interface LoginService {
    //GET 예제
    @FormUrlEncoded
    @POST("/auth_D/login")
    fun getUser(
        @Field("id") id: String,
        @Field("password") password: String, //password는 안받아도 됨 (hash값이라 받아오기도 껄끄럽다)
        @Field("nick") nick: String,
        @Field("name") name: String,
        @Field("age") age: Int,

        @Field("success") success: String,
        @Field("msg") msg: String,
    ): Call<UserInfo>
}