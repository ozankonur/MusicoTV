package com.ozan.musicotv.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("feed")
    @Expose
    var feed: Feed
)