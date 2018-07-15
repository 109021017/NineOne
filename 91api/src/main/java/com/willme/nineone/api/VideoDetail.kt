package com.willme.nineone.api

import java.io.Serializable

/**
 * Created by Wen on 02/03/2018.
 */
data class VideoDetail(
        val videoUrl: String,
        val videoId: String,
        val ownerId: String,
        val thumbImgUrl: String,
        val videoName: String,
        val ownerName: String,
        val addDate: String,
        val userOtherInfo: String
): Serializable