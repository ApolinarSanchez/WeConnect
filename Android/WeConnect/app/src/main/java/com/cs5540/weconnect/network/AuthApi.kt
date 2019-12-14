package com.cs5540.weconnect.network

import com.cs5540.weconnect.ui.profile.Profile
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password:String,
        @Field("school") school:String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("user/signin")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ):Call<LoginResponse>
}