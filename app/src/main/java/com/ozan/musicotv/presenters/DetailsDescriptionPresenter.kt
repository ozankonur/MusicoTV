package com.ozan.musicotv.presenters

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.ozan.musicotv.data.model.LocalResult

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: ViewHolder,
        item: Any
    ) {
        val music = item as LocalResult

        viewHolder.title.text = music.artistName
        viewHolder.subtitle.text = music.releaseDate
        viewHolder.body.text = music.contentAdvisoryRating
    }
}