package com.ozan.musicotv.data.model

import java.io.Serializable

data class LocalResult(
    var artistId: String,
    var artistName: String,
    var artistUrl: String,
    var artworkUrl100: String,
    var contentAdvisoryRating: String,
    var localGenres: List<LocalGenre>,
    var id: String,
    var kind: String,
    var name: String,
    var releaseDate: String,
    var url: String
) : Serializable