package com.ozan.musicotv.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.ozan.musicotv.R
import com.ozan.musicotv.activites.BrowseErrorActivity
import com.ozan.musicotv.activites.DetailsActivity
import com.ozan.musicotv.data.network.entity.Result
import com.ozan.musicotv.presenters.CardPresenter
import com.ozan.musicotv.viewModel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.internal.notify

@AndroidEntryPoint
class MainFragment : BrowseSupportFragment() {

    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private val viewModel: ViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callMusics()

        prepareBackgroundManager()

        setupUIElements()

        loadRows()

        setupEventListeners()
    }

    private fun callMusics() {
        viewModel.getMusics()
    }

    private fun prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(requireActivity().window)
        mDefaultBackground = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.musicback
        )
        mMetrics = DisplayMetrics()

        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)

        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        brandColor = ContextCompat.getColor(requireActivity(), R.color.fastlane_background)
        searchAffordanceColor = ContextCompat.getColor(requireActivity(), R.color.search_opaque)
    }

    private fun loadRows() {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()
        var allMusicSize = 0
        viewModel.musicResponse.observe(viewLifecycleOwner, {
            rowsAdapter.clear()
            viewModel.musicResponse.value.let { allMusics ->
                if (allMusics != null) {
                    allMusicSize = allMusics.size + 1
                    if (allMusics.isNotEmpty()) {
                        val musicList = allMusics.toList()
                        for (i in 0 until allMusics.size) {
                            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
                            for (j in 0 until musicList[i].second.size) {
                                listRowAdapter.add(musicList[i].second[j])
                            }
                            val header = HeaderItem(i.toLong(), musicList[i].first)
                            rowsAdapter.add(ListRow(header, listRowAdapter))
                        }
                        val gridHeader = HeaderItem(allMusicSize.toLong(), "Clear Search")

                        val mGridPresenter = GridItemPresenter()
                        val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)
                        gridRowAdapter.add(resources.getString(R.string.clear_filter))
                        rowsAdapter.add(ListRow(gridHeader, gridRowAdapter))

                        rowsAdapter.notifyArrayItemRangeChanged(0, rowsAdapter.size());
                    }
                }
            }
        })

        adapter = rowsAdapter
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            showSearchDialog()
        }

        onItemViewClickedListener = ItemViewClickedListener()
    }

    @ExperimentalCoroutinesApi
    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {

            if (item is Result) {
                val intent = Intent(requireActivity(), DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.Music, item)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME
                )
                    .toBundle()
                startActivity(intent, bundle)
            } else if (item is String) {
                if (item.contains(getString(R.string.clear_filter))) {
                    viewModel.search("")
                } else {
                    Toast.makeText(requireActivity(), item, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun showSearchDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Search Music")

        val input = EditText(activity)
        input.setSingleLine()

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(16, 10, 16, 10)
        input.layoutParams = params

        input.hint = "search.."
        input.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            viewModel.search(input.text.toString())
        }
        builder.setNegativeButton(
            "Clear"
        ) { dialog, _ ->
            viewModel.search("")
            dialog.cancel()
        }

        builder.show()
    }


    private inner class GridItemPresenter : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(200, 200)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.default_background))
            view.setTextColor(Color.WHITE)
            view.gravity = Gravity.CENTER
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
            (viewHolder.view as TextView).text = item as String
        }

        override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
    }
}