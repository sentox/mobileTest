package com.sentox.mobiletest.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 描述：航段从表
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BookingEntity::class,
            parentColumns = ["shipReference"],
            childColumns = ["shipReference"],
            onDelete = ForeignKey.CASCADE  // 主表数据删除时，自动删除关联的segments
        )
    ],
    indices = [
        Index(value = ["shipReference"])  // 为外键创建索引，提高查询性能
    ]
)
data class SegmentEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int,  // 主键
    val shipReference: String,  // 外键

    val id: Int,  //这是下发的参数
    // 出发地信息
    val originCode: String,
    val originDisplayName: String,
    val originUrl: String,
    val originCity: String,
    // 目的地信息
    val destinationCode: String,
    val destinationDisplayName: String,
    val destinationUrl: String,
    val destinationCity: String

)
