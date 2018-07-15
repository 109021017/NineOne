/*
 * Copyright (c) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.willme.nineone.tv.main

import android.content.Intent
import android.os.Bundle
import android.support.v17.leanback.app.BrowseSupportFragment
import android.support.v17.leanback.widget.*
import android.support.v4.content.ContextCompat
import com.willme.nineone.api.Video
import com.willme.nineone.tv.CardPresenter
import com.willme.nineone.tv.R
import com.willme.nineone.tv.common.CommonContract
import com.willme.nineone.tv.common.CommonView
import com.willme.nineone.tv.play.PlaybackActivity
import java.util.*

/*
 * Main class to show BrowseFragment with header and rows of videos
 */
class MainFragment : BrowseSupportFragment() {

    private val mRowPresenters: ArrayList<CommonContract.Presenter> = ArrayList()
    private lateinit var mRowsAdapter: ArrayObjectAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUIElements()
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mRowsAdapter
        for (i in 0 until CATEGORY_DEFAULT_91PORN_VALUE.size){
            val listRowAdapter = ArrayObjectAdapter(CardPresenter())
            val commonView = CommonView(listRowAdapter)

            val value = CATEGORY_DEFAULT_91PORN_VALUE[i]
            val name = ROW_HEADER_NAMES[i]
            mRowsAdapter.add(ListRow(HeaderItem(i.toLong(), name), listRowAdapter))
            val m = if(value == "top1") "-1" else ""
            val commonPresenter =
                    if (i == 0)
                        IndexPresenter(commonView)
                    else
                        CategoryPresenter(commonView, value, m)
            commonView.setPresenter(commonPresenter as CommonContract.Presenter)
            mRowPresenters.add(commonPresenter)
        }
        loadData()
        setupEventListeners()
        prepareEntranceTransition()
    }

    private fun setupUIElements() {
        headersState = BrowseSupportFragment.HEADERS_ENABLED
        badgeDrawable = activity!!.resources.getDrawable(R.drawable.title, null)
        isHeadersTransitionOnBackEnabled = true
        searchAffordanceColor = ContextCompat.getColor(activity!!, R.color.search)
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            activity!!.onSearchRequested()
        }
        onItemViewClickedListener = ItemViewClickedListener()
    }

    private fun loadData(){
        mRowPresenters.forEach {
            it.subscribe()
        }
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any,
                                   rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            if (item is Video) {
                val intent = Intent(activity, PlaybackActivity::class.java)
                intent.putExtra(PlaybackActivity.EXTRA_VIDEO, item)
                activity!!.startActivity(intent)
            }
        }
    }

    companion object {
        private val CATEGORY_DEFAULT_91PORN_VALUE = arrayOf("", "hot", "mf", "top", "top1")
        private val ROW_HEADER_NAMES = arrayOf("主页", "当前最热", "收藏最多", "本月最热", "上月最热")
    }
}
