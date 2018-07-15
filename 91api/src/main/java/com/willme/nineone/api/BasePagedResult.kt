package com.willme.nineone.api

/**
 * Created by Wen on 02/03/2018.
 */
data class BasePagedResult<out T>(
        val data: T,
        val totalPage:Int
)