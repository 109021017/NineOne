package com.willme.nineone.tv.common

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Wen on 2018/7/15.
 */
abstract class CommonPresenter( private var view: CommonContract.View ): CommonContract.Presenter{

    protected val mCompositeDisposable = CompositeDisposable()
    protected var page = 1
    protected var totalPage = 2

    override fun subscribe() {
        getData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.dispose()
    }

    override fun getData() {
        view.clearList()
        page = 1
        listMore()
    }

    override fun hasMore() : Boolean {
        return page < totalPage
    }

}