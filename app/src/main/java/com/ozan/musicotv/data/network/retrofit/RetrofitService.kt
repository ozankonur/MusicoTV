package com.ozan.musicotv.data.network.retrofit

import com.ozan.musicotv.data.network.entity.MusicResponse


interface RetrofitService {
    suspend fun getQueryResult(): MusicResponse
}