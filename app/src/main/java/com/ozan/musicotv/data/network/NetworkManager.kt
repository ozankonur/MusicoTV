package com.ozan.musicotv.data.network

import com.ozan.musicotv.data.network.entity.MusicResponse


interface NetworkManager {
    suspend fun getQueryResult() : MusicResponse
}