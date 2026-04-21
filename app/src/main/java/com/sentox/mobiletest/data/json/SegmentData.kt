package com.sentox.mobiletest.data.json

/**
 * 描述：航段信息
 * 说明：用于json转换
 * Created by Sentox
 * Created on 2026/4/21
 */
data class SegmentData(
    /**
     *  航段数
     * **/
    val id: Int,
    /**
     *  航段来源和目标地
     * **/
    val originAndDestinationPair: OriginAndDestinationData
)