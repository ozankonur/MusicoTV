package com.ozan.musicotv.presenters

import android.util.TypedValue
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.ozan.musicotv.data.network.entity.Result

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: ViewHolder,
        item: Any
    ) {
        val music = item as Result
        viewHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50F)
        viewHolder.subtitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
        viewHolder.body.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)

        viewHolder.title.text = music.name
        viewHolder.subtitle.text = music.artistName
        viewHolder.body.text = music.artistUrl
    }
}