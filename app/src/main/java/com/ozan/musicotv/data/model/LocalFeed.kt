package com.ozan.musicotv.data.model

data class LocalFeed(
    var localAuthor: LocalAuthor,
    var copyright: String,
    var country: String,
    var icon: String,
    var id: String,
    var localLinks: List<LocalLink>,
    var localResults: List<LocalResult>,
    var title: String,
    var updated: String
)