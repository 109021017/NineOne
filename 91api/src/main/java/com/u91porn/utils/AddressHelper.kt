package com.u91porn.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.u91porn.utils.constants.Keys
import java.util.*

/**
 * @author flymegoc
 * @date 2018/1/26
 */

class AddressHelper private constructor(context: Context) {
    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences("com.u91porn.91api", Context.MODE_PRIVATE)
    val video91PornAddress: String
        get() = mSharedPreferences.getString(Keys.KEY_SP_CUSTOM_ADDRESS, "")

    companion object {
        private val TAG = AddressHelper::class.java.simpleName
        /**
         * must be call init before
         *
         * @return ob
         */
        @SuppressLint("StaticFieldLeak")
        var instance: AddressHelper? = null
            private set
        private val mRandom = Random()

        /**
         * init
         *
         * @param ctx must be application context
         */
        fun init(ctx: Context) {
            if (instance == null) {
                synchronized(AddressHelper::class.java) {
                    instance = AddressHelper(ctx)
                }
            }
        }

        /**
         * 获取随机ip地址
         *
         * @return
         */
        val randomIPAddress: String
            get() = mRandom.nextInt(255).toString() + "." + mRandom.nextInt(255).toString() + "." + mRandom.nextInt(255).toString() + "." + mRandom.nextInt(255).toString()
    }

}
