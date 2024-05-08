package com.shoxruxbek.wallpapers.retrofit

import com.shoxruxbek.wallpapers.models.Rasmlar
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
        @Query("per_page") perPage: Int,
        @Query("safesearch") safesearch:Boolean
    ): Rasmlar




    @GET("/api/")
    fun getImageResults(
        @Query("key") key: String?,
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<Rasmlar?>?



}