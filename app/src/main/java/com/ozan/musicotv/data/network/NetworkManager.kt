package com.ozan.musicotv.data.network

import com.ozan.musicotv.data.model.LocalMusicResponse


interface NetworkManager {
    suspend fun getQueryResult() : LocalMusicResponse
}