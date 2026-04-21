package com.sentox.mobiletest.data.json

import com.google.gson.Gson

/**
 * 描述：模拟返回的数据结构
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
data class BookingListResponse(val bookings: ArrayList<BookingData>) {
    fun toJson(): String = Gson().toJson(this)
}

