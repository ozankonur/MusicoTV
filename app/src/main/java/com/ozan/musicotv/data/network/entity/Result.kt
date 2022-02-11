package com.ozan.musicotv.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("artistId")
    @Expose
    var artistId: String,
    @SerializedName("artistName")
    @Expose
    var artistName: String,
    @SerializedName("artistUrl")
    @Expose
    var artistUrl: String,
    @SerializedName("artworkUrl100")
    @Expose
    var artworkUrl100: String,
    @SerializedName("contentAdvisoryRating")
    @Expose
    var contentAdvisoryRating: String,
    @SerializedName("genres")
    @Expose
    var genres: List<Genre>,
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("kind")
    @Expose
    var kind: String,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("releaseDate")
    @Expose
    var releaseDate: String,
    @SerializedName("url")
    @Expose
    var url: String
)