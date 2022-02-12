package com.ozan.musicotv.repository

import android.util.Log
import com.ozan.musicotv.data.network.NetworkManager
import com.ozan.musicotv.data.network.entity.MusicResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val networkManager: NetworkManager
) {
    suspend fun getQueryResult(): Flow<MusicResponse> =
        flow {
            delay(100)
            try{
                val musicResult = networkManager.getQueryResult()
                emit(musicResult)
            }
            catch (e: Exception){
              // emit()
                Log.d("LOLLLLL ex",e.localizedMessage)
            }
        }
}