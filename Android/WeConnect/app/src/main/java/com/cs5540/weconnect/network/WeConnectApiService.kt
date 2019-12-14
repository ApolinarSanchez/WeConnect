package com.cs5540.weconnect.network

import android.util.Log
import com.cs5540.weconnect.ui.homepage.Category
import com.cs5540.weconnect.ui.profile.Profile
import com.cs5540.weconnect.ui.projects.Project
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import com.squareup.moshi.Json
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import retrofit2.http.Body

private const val BASE_URL = "https://us-central1-we-connect-stage.cloudfunctions.net/resource/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

//for login
object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: AuthApi by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(AuthApi::class.java)
    }
}

data class Credential(var email: String, var password:String){
    fun getDetail():String{
        var result : String = email + password
        return result;
    }
}

data class LoginResponse(
    val error: Boolean,
    var message:String,
    var email: String,
    var kind: String,
    var localId: String,
    var displayName: String,
    var idToken: String,
    var registered: Boolean,
    var refreshToken: Boolean
)

data class DefaultResponse(val error: Boolean, val message:String)

interface WeConnectApiService {
    @GET("user")
    fun getProfiles():
            Deferred<List<Profile>>
    @GET("category")
    fun getCategories():
            Deferred<List<Category>>
    @GET("project")
    fun getProjects():
            Deferred<List<Project>>
    @GET("project/{categoryId}/category")
    fun getProjectsByCategory(
        @Path("categoryId") categoryId : String?):
            Deferred<List<Project>>

//    @Headers("Content-Type: application/json",
//        "Accept: application/json")
//    @POST("user/signin")
//    fun login(@Body body: Credential):
//            Deferred<List<Profile>>

    @FormUrlEncoded
    @POST("user/signIn")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Deferred<LoginResponse>
}
object WeConnectApi {
    val retrofitService : WeConnectApiService by lazy {
        retrofit.create(WeConnectApiService::class.java)}
}
