package com.ozan.musicotv.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.ozan.musicotv.*
import com.ozan.musicotv.activites.DetailsActivity
import com.ozan.musicotv.activites.MainActivity
import com.ozan.musicotv.data.network.entity.Result
import com.ozan.musicotv.presenters.DetailsDescriptionPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt
import android.net.Uri




@AndroidEntryPoint
class MusicDetailsFragment : DetailsSupportFragment() {

    private var mSelectedMusic: Result? = null
    private lateinit var mDetailsBackground: DetailsSupportFragmentBackgroundController
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate DetailsFragment")
        super.onCreate(savedInstanceState)

        mDetailsBackground = DetailsSupportFragmentBackgroundController(this)

        mSelectedMusic =
            requireActivity().intent.getSerializableExtra(DetailsActivity.Music) as Result
        if (mSelectedMusic != null) {
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
            adapter = mAdapter
            initializeBackground(mSelectedMusic)
            onItemViewClickedListener = ItemViewClickedListener()
        } else {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeBackground(result: Result?) {
        mDetailsBackground.enableParallax()
        val highImage = result?.artworkUrl100?.replace("100x100bb.jpg","400x400bb.jpg")
        Glide.with(requireActivity())
            .asBitmap()
            .fitCenter()
            .error(R.drawable.default_background)
            .load(highImage)
            .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    mDetailsBackground.coverBitmap = bitmap
                    mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                }
            })
    }

    private fun setupDetailsOverviewRow() {
        Log.d(TAG, "doInBackground: " + mSelectedMusic?.toString())
        val row = DetailsOverviewRow(mSelectedMusic)
        row.imageDrawable = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.default_background
        )
        val width = convertDpToPixel(requireActivity(), DETAIL_THUMB_WIDTH)
        val height = convertDpToPixel(requireActivity(), DETAIL_THUMB_HEIGHT)
        Glide.with(requireActivity())
            .load(mSelectedMusic?.artworkUrl100)
            .fitCenter()
            .error(R.drawable.default_background)
            .into<CustomTarget<Drawable>>(object : CustomTarget<Drawable>(width, height) {
                override fun onResourceReady(
                    drawable: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    row.imageDrawable = drawable
                    mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        val actionAdapter = ArrayObjectAdapter()

        actionAdapter.add(
            Action(
                ACTION_LISTEN,
                resources.getString(R.string.listen_trailer)
            )
        )
        actionAdapter.add(
            Action(
                ACTION_SHARE,
                resources.getString(R.string.listen_share),
            )
        )
        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor =
            ContextCompat.getColor(requireActivity(), R.color.selected_background)

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            activity, DetailsActivity.SHARED_ELEMENT_NAME
        )
        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true
        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_LISTEN) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedMusic?.url))
                startActivity(browserIntent)
            } else {
                Toast.makeText(requireActivity(), action.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is Result) {
                val intent = Intent(requireActivity(), DetailsActivity::class.java)
                intent.putExtra(resources.getString(R.string.Music), mSelectedMusic)

                val bundle =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        requireActivity(),
                        (itemViewHolder?.view as ImageCardView).mainImageView,
                        DetailsActivity.SHARED_ELEMENT_NAME
                    )
                        .toBundle()
                startActivity(intent, bundle)
            }
        }
    }

    companion object {
        private const val TAG = "VideoDetailsFragment"

        private const val ACTION_LISTEN = 1L
        private const val ACTION_SHARE = 2L

        private const val DETAIL_THUMB_WIDTH = 500
        private const val DETAIL_THUMB_HEIGHT = 500
    }
}