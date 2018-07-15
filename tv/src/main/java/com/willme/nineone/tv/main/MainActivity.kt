package com.willme.nineone.tv.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, MainFragment())
                .commit()
    }

    override fun onSearchRequested(): Boolean {
        return true
    }

}
