package com.sentox.mobiletest.data.json


/**
 * 描述：航段数据
 * 说明：用于json转换
 * Created by Sentox
 * Created on 2026/4/21
 */
data class OriginAndDestinationData(
    val destination: Location,
    val destinationCity: String,
    val origin: Location,
    val originCity: String
)

/**
 *  目的地数据
 * **/
data class Location(
    /**
     *  机场代码
     * **/
    val code: String,
    /**
     *  城市名
     * **/
    val displayName: String,
    val url: String = "www.ship.com"
)
