package com.ozan.musicotv.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("url")
    @Expose
    var url: String
)