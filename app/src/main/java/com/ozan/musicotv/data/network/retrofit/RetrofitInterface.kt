package com.ozan.musicotv.data.network.retrofit


import com.ozan.musicotv.data.network.entity.MusicResponse
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("us/music/most-played/50/albums.json")
    suspend fun getSearchResult(): MusicResponse
}