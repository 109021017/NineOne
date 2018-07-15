package com.willme.nineone.tv.main

import com.u91porn.data.ApiManager
import com.u91porn.parser.Parse91PronVideo
import com.u91porn.utils.HeaderUtils
import com.willme.nineone.tv.common.CommonContract
import com.willme.nineone.tv.common.CommonPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Wen on 2018/7/15.
 */
class IndexPresenter(var view: CommonContract.View): CommonPresenter(view) {

    override fun listMore() {
        val disposable = ApiManager.getInstance()
                .noLimit91PornService
                .indexPhp(HeaderUtils.indexHeader)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Parse91PronVideo.parseIndex(it)
                }
                .subscribe (
                        {
                            totalPage = 1
                            view.addResults(it)
                        },
                        {
                            it.printStackTrace()
                            view.showNetworkError()
                        }
                )
        mCompositeDisposable.add(disposable)
    }

}