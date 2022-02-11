package com.ozan.musicotv.data.network.retrofit

import com.ozan.musicotv.data.network.entity.MusicResponse

class RetrofitServiceImpl constructor(
    private val retrofitInterface: RetrofitInterface
) : RetrofitService {
    override suspend fun getQueryResult(): MusicResponse {
        return retrofitInterface.getSearchResult()
    }
}