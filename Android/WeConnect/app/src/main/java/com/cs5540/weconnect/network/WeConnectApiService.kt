package com.cs5540.weconnect.network

import com.cs5540.weconnect.ui.homepage.Category
import com.cs5540.weconnect.ui.profile.Profile
import com.cs5540.weconnect.ui.projects.Project
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred

import retrofit2.Callback

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://us-central1-we-connect-stage.cloudfunctions.net/resource/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

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

}

object WeConnectApi {
    val retrofitService : WeConnectApiService by lazy {
        retrofit.create(WeConnectApiService::class.java)}
}
