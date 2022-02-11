package com.ozan.musicotv.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("author")
    @Expose
    var author: Author,
    @SerializedName("copyright")
    @Expose
    var copyright: String,
    @SerializedName("country")
    @Expose
    var country: String,
    @SerializedName("icon")
    @Expose
    var icon: String,
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("links")
    @Expose
    var links: List<Link>,
    @SerializedName("results")
    @Expose
    var results: List<Result>,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("updated")
    @Expose
    var updated: String
)