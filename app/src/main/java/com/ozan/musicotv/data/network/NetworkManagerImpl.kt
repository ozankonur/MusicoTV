package com.ozan.musicotv.data.network

import com.ozan.musicotv.data.model.LocalMusicResponse
import com.ozan.musicotv.data.network.retrofit.RetrofitService
import com.ozan.musicotv.data.network.util.ApiMapper

class NetworkManagerImpl constructor(
    private val retrofitService: RetrofitService,
    private val apiMapper: ApiMapper
) : NetworkManager {
    override suspend fun getQueryResult(): LocalMusicResponse {
        return apiMapper.modelFromEntity(retrofitService.getQueryResult())
    }
}