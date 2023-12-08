package com.test.mabale_ciedner_semi_final

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TweetApiService {
    @GET("tweet/{lastname}/")
    fun listTweets(@Path("lastname") lastname: String): Call<List<Tweet>>

    @POST("tweet/mabale/")
    fun createTweet(@Body newTweet: Tweet): Call<Tweet>


    @PUT("tweet/mabale/{id}")
    fun updateTweet(@Path("id") id: String, @Body tweet: Tweet): Call<Tweet>

    @DELETE("tweet/mabale/{id}")
    fun deleteTweet(@Path("id") id: String, @Query("userName") userName: String, @Body tweet: Tweet): Call<Unit>
}