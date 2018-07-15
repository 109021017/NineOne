package com.willme.nineone.tv.play

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.willme.nineone.api.Video

/**
 * Created by Wen on 2018/6/27.
 */
class PlaybackActivity : FragmentActivity() {

    companion object {
        const val EXTRA_VIDEO = "video"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlaybackFragment.newInstance(intent.extras[EXTRA_VIDEO] as Video).let {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, it)
                    .commit()
        }
    }

}