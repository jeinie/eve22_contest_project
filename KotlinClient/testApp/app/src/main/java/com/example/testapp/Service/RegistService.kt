package com.example.testapp.Service

import com.example.testapp.Model.UserModel.UserInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistService {
    //POST
    @FormUrlEncoded
    @POST("/auth_C/join")
    fun RegistUser(
        @Field("id") id: String,
        @Field("password") password: String,
        @Field("nick") nick: String,
        @Field("name") name: String,
        @Field("age") age: Int,

        @Field("success") success: String,
        @Field("msg") msg: String,
    ): Call<UserInfo>
}