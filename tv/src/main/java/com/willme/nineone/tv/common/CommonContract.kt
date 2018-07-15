package com.willme.nineone.tv.common

import com.willme.nineone.api.Video
import com.willme.nineone.mvp.BasePresenter
import com.willme.nineone.mvp.BaseView

/**
 * Created by Wen on 2018/6/22.
 */
class CommonContract {
    interface Presenter: BasePresenter {
        fun getData()
        fun hasMore(): Boolean
        fun listMore()
    }

    interface View: BaseView<Presenter> {
        fun clearList()
        fun showResults(results: List<Video>)
        fun addResults(result: List<Video>)
        fun showNetworkError()
        fun setEmptyContentVisibility()
        fun refreshMore()
    }
}