package com.willme.ijkplayer

/**
 * Created by Wen on 2018/6/26.
 */

interface ISettings {

    fun getUsingMediaDataSource() : Boolean
    fun getEnableSurfaceView() : Boolean
    fun getEnableTextureView() : Boolean
    fun getEnableNoView() : Boolean
    fun getUsingOpenSLES() : Boolean
    fun getMediaCodecHandleResolutionChange() : Boolean
    fun getEnableDetachedSurfaceTextureView() : Boolean
    fun getPixelFormat() : String
    fun getUsingMediaCodecAutoRotate() : Boolean
    fun getUsingMediaCodec() : Boolean
}

class DefaultSettings : ISettings {
    override fun getUsingMediaDataSource(): Boolean {
        return true
    }

    override fun getEnableSurfaceView(): Boolean {
        return true
    }

    override fun getEnableTextureView(): Boolean {
        return false
    }

    override fun getEnableNoView(): Boolean {
        return false
    }

    override fun getUsingOpenSLES(): Boolean {
        return true
    }

    override fun getMediaCodecHandleResolutionChange(): Boolean {
        return true
    }

    override fun getEnableDetachedSurfaceTextureView(): Boolean {
        return false
    }

    override fun getPixelFormat(): String {
        return ""
    }

    override fun getUsingMediaCodecAutoRotate(): Boolean {
        return true
    }

    override fun getUsingMediaCodec(): Boolean {
        return false
    }

}