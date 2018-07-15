package com.willme.nineone.tv.main

import com.u91porn.data.ApiManager
import com.u91porn.parser.Parse91PronVideo
import com.u91porn.utils.HeaderUtils
import com.willme.nineone.tv.common.CommonContract
import com.willme.nineone.tv.common.CommonPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Wen on 2018/6/22.
 */
class CategoryPresenter(
        private var view: CommonContract.View,
        private var category: String,
        private var m: String
) : CommonPresenter(view) {

    override fun listMore() {
        val viewType = "basic"
        val disposable = ApiManager.getInstance().noLimit91PornService
                .getCategoryPage(category, viewType, page, m, HeaderUtils.indexHeader)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Parse91PronVideo.parseHot(it)
                }.subscribe (
                        {
                            totalPage = it.totalPage
                            view.addResults(it.data)
                        },
                        {
                            it.printStackTrace()
                            view.showNetworkError()
                        }
                )
        mCompositeDisposable.add(disposable)
    }

}