package com.willme.nineone.mvp

/**
 * Created by Wen on 28/02/2018.
 */

interface BaseView<in T> {

    fun setPresenter(presenter : T)

}