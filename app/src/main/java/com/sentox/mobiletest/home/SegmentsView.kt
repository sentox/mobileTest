package com.sentox.mobiletest.home

import android.content.Context
import android.util.AttributeSet
import com.sentox.mobiletest.R
import com.sentox.mobiletest.base.application.BaseApplication
import com.sentox.mobiletest.base.view.BaseFrameLayout
import com.sentox.mobiletest.data.database.entity.SegmentEntity
import com.sentox.mobiletest.databinding.ItemBookingSegmentBinding

/**
 * 描述：航段自定义view
 * 说明：
 * Created by Sentox
 * Created on 2026/4/23
 */
class SegmentsView @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null
) : BaseFrameLayout<ItemBookingSegmentBinding>(context, attrs) {

    fun setData(data: SegmentEntity) {
        binding?.apply {
            mTvOrigin.text = data.originDisplayName
            mTvDestination.text = data.destinationDisplayName
            mTvDesc.text = BaseApplication.getAppContext()
                .getString(R.string.booking_desc, data.originCode, data.destinationCode, data.originUrl)
            mTvId.text = data.id.toString()
        }
    }
}