package com.willme.nineone.tv.common

import android.support.v17.leanback.widget.ArrayObjectAdapter
import com.willme.nineone.api.Video

/**
 * Created by Wen on 2018/6/22.
 */
class CommonView(private var listRowAdapter : ArrayObjectAdapter) : CommonContract.View {

    private lateinit var presenter: CommonContract.Presenter

    override fun setPresenter(presenter: CommonContract.Presenter) {
        this.presenter = presenter
    }

    override fun clearList() {
        listRowAdapter.clear()
        listRowAdapter.notifyArrayItemRangeChanged(0, listRowAdapter.size())
    }

    override fun showResults(results: List<Video>) {
        listRowAdapter.clear()
        val origSize = listRowAdapter.size()
        listRowAdapter.addAll(origSize, results)
        listRowAdapter.notifyArrayItemRangeChanged(0, Math.max(origSize, results.size))
    }

    override fun addResults(result: List<Video>) {
        val origSize = listRowAdapter.size()
        listRowAdapter.addAll(origSize, result)
        listRowAdapter.notifyArrayItemRangeChanged(origSize, result.size)
    }

    override fun showNetworkError() {

    }

    override fun setEmptyContentVisibility() {

    }

    override fun refreshMore() {
        if(presenter.hasMore()){
            presenter.listMore()
        }
    }

}