package com.ozan.musicotv.data.network.util

import com.ozan.musicotv.data.model.LocalMusicResponse
import com.ozan.musicotv.data.network.entity.MusicResponse
import com.ozan.musicotv.util.mapper.EntityMapper

//TODO implement

class ApiMapper : EntityMapper<MusicResponse, LocalMusicResponse> {
    override fun modelFromEntity(entity: MusicResponse): LocalMusicResponse {
        TODO("Not yet implemented")
    }

    override fun modelToEntity(model: LocalMusicResponse): MusicResponse {
        TODO("Not yet implemented")
    }
}