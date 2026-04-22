package com.sentox.mobiletest.data.database

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.sentox.mobiletest.data.database.entity.BookingListData
import com.sentox.mobiletest.data.json.BookingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 描述：数据库工具类
 * 说明：
 * Created by Sentox
 * Created on 2026/4/22
 */
object DataProvider {

    private val mDb = AppDatabase.getInstance()
    private val mDao = mDb.getBookingDao()

    /**
     *  获取全部订单数据
     * **/
    fun getAllBookingListData(): Flow<List<BookingListData>> {
        return flow {
            emit(mDao.getAllBookingListData())
        }.flowOn(Dispatchers.IO)
    }

    /**
     *  插入或更新单个数据
     * **/
    suspend fun insertOrUpdateBookListData(bookingListData: BookingListData) {
        mDao.insertOrUpdateBookListData(bookingListData)
    }

    /**
     *  删除单个数据，从表数据会联级删除
     * **/
    suspend fun deleteBookingListData(shipReference: String){
        mDao.deleteBookingByReference(shipReference)
    }
}