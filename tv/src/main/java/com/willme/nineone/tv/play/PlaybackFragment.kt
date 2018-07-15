package com.willme.nineone.tv.play

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v17.leanback.app.VideoSupportFragment
import android.support.v17.leanback.app.VideoSupportFragmentGlueHost
import android.support.v17.leanback.media.PlaybackGlue
import android.support.v17.leanback.supportleanbackshowcase.app.media.ExoPlayerAdapter
import android.support.v17.leanback.supportleanbackshowcase.app.media.PlaybackSeekDiskDataProvider
import android.support.v17.leanback.supportleanbackshowcase.app.media.VideoMediaPlayerGlue
import android.text.TextUtils
import com.u91porn.data.ApiManager
import com.u91porn.parser.Parse91PronVideo
import com.u91porn.utils.AddressHelper
import com.u91porn.utils.HeaderUtils
import com.willme.nineone.api.Video
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Wen on 2018/6/26.
 */

class PlaybackFragment : VideoSupportFragment() {

    companion object {

        fun newInstance(video: Video) : PlaybackFragment {
            val args = Bundle().apply {
                putSerializable(PlaybackActivity.EXTRA_VIDEO, video)
            }
            return PlaybackFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var mMediaPlayerGlue: VideoMediaPlayerGlue<ExoPlayerAdapter>
    private val mHost = VideoSupportFragmentGlueHost(this)

    lateinit var mVideo: Video

    private fun playWhenReady(glue: PlaybackGlue) {
        if (glue.isPrepared) {
            glue.play()
        } else {
            glue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
                override fun onPreparedStateChanged(glue: PlaybackGlue?) {
                    if (glue!!.isPrepared) {
                        glue.removePlayerCallback(this)
                        glue.play()
                    }
                }
            })
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        if(isInPictureInPictureMode){
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerAdapter = ExoPlayerAdapter(activity)
        playerAdapter.audioStreamType = AudioManager.USE_DEFAULT_STREAM_TYPE
        mMediaPlayerGlue = VideoMediaPlayerGlue(activity, playerAdapter)
        mMediaPlayerGlue.host = mHost
        val audioManager = activity!!
                .getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.requestAudioFocus(
                    AudioFocusRequest
                            .Builder(AudioManager.STREAM_MUSIC)
                            .build()
            )
        }
        mVideo = arguments!![PlaybackActivity.EXTRA_VIDEO] as Video
        loadVideo(mVideo)
        backgroundType = BG_LIGHT
    }

    private fun resetWatchTime(forceReset: Boolean) {
        val sharedPrefsCookiePersistor = ApiManager.getInstance().sharedPrefsCookiePersistor
        val cookieList = sharedPrefsCookiePersistor.loadAll()
        cookieList.filter {
            "watch_times" == it.name() && TextUtils.isDigitsOnly(it.value())
        }.filter {
                    if(forceReset){
                        sharedPrefsCookiePersistor.delete(it)
                    }
                    it.value().toInt() >= 8
        }.forEach {
                    sharedPrefsCookiePersistor.delete(it)
                }
    }

    private fun loadVideo(video: Video) {
        val ip = AddressHelper.randomIPAddress
        ApiManager.getInstance()
                .noLimit91PornService
                .getVideoPlayPage(video.getViewKey(), ip, HeaderUtils.indexHeader)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    resetWatchTime(false)
                    Parse91PronVideo.parseVideoPlayUrl(it)
                }
                .subscribe {
                    mMediaPlayerGlue.title = it.videoName
                    mMediaPlayerGlue.subtitle = it.ownerName
                    mMediaPlayerGlue.playerAdapter.setDataSource(Uri.parse(it.videoUrl))
                    PlaybackSeekDiskDataProvider.setDemoSeekProvider(mMediaPlayerGlue)
                    playWhenReady(mMediaPlayerGlue)
                }
    }

    override fun onPause() {
        super.onPause()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity!!.isInPictureInPictureMode){
            hideControlsOverlay(false)
        } else {
            mMediaPlayerGlue.pause()
        }
    }

    override fun onVideoSizeChanged(width: Int, height: Int) {
    }



}