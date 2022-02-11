package com.ozan.musicotv.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("self")
    @Expose
    var self: String
)