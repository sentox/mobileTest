package com.sentox.mobiletest.service

import com.sentox.mobiletest.data.json.BookingData

/**
 * 描述：模拟的预定列表信息返回类
 * 说明：
 * Created by Sentox
 * Created on 2026/4/22
 */
class BookingListResponse {

    var isSuccess = false
    var rspCode = 443
    var msg = ""
    /**
     *  模拟返回的未解析json数据
     * **/
    var data = arrayListOf<BookingData>()
}