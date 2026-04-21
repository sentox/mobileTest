package com.sentox.mobiletest.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 描述：数据库订单主表
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
@Entity
data class BookingEntity(
    @PrimaryKey
    val shipReference: String,
    val shipToken: String,
    val canIssueTicketChecking: Boolean,
    val expiryTime: Long,
    val duration: Long
)
