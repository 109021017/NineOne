package com.willme.nineone.api

import java.io.Serializable

/**
 * Created by Wen on 02/03/2018.
 */
data class Video(
        val videoName: String,
        val thumbImgUrl: String,
        val videoUrl: String,
        val info: String,
        val durationStr: String
): Serializable{
    fun getViewKey(): String {
        return videoUrl.substringAfter("=")
    }
}
