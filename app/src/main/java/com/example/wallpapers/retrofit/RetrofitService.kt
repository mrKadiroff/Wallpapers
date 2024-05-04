package com.example.wallpapers.retrofit

import com.example.wallpapers.models.Rasmlar
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface RetrofitService {

    @GET("api")
    suspend fun getListPhotos(
        @Query("key") key: String,
        @Query("q") q:String,
        @Query("image_type") image_type:String,
        @Query("pretty") pretty:Boolean,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Rasmlar




    @GET("/api/")
    fun getImageResults(
        @Query("key") key: String?,
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<Rasmlar?>?



}