package com.ozan.musicotv.data.network

import com.ozan.musicotv.data.network.entity.MusicResponse
import com.ozan.musicotv.data.network.retrofit.RetrofitService

class NetworkManagerImpl constructor(
    private val retrofitService: RetrofitService,
) : NetworkManager {
    override suspend fun getQueryResult(): MusicResponse {
        return retrofitService.getQueryResult()
    }
}