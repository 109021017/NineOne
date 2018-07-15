package com.willme.nineone.tv

import android.app.Application
import com.u91porn.data.ApiManager

/**
 * Created by Wen on 2018/7/14.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiManager.init(this)
    }

}