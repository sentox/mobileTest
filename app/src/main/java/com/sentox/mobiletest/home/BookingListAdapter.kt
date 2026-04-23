package com.sentox.mobiletest.home

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sentox.mobiletest.data.database.entity.BookingListData
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sentox.mobiletest.R
import com.sentox.mobiletest.base.application.BaseApplication

/**
 * 描述：订单列表adapter
 * 说明：
 * Created by Sentox
 * Created on 2026/4/23
 */
class BookingListAdapter : BaseQuickAdapter<BookingListData, BaseViewHolder>(R.layout.item_booking_list) {


    override fun convert(holder: BaseViewHolder, item: BookingListData) {
        holder.getView<TextView>(R.id.mTvShipReference).text =
            BaseApplication.getAppContext().getString(R.string.booking_reference, item.booking.shipReference)
        holder.getView<TextView>(R.id.mTvDuration).text =
            BaseApplication.getAppContext().getString(R.string.booking_duration, (item.booking.duration / 60).toString())
        holder.getView<TextView>(R.id.mTvStatus).apply {
            if (item.booking.expiryTime > System.currentTimeMillis() / 1000) {
                text = if (item.booking.canIssueTicketChecking) {
                    BaseApplication.getAppContext().getString(R.string.booking_status_issued)
                } else {
                    BaseApplication.getAppContext().getString(R.string.booking_status_not_issued)
                }
                setTextColor(Color.parseColor("#0093d0"))
            } else {
                text = BaseApplication.getAppContext().getString(R.string.booking_status_expiry)
                setTextColor(Color.parseColor("#F56C6C"))
            }
        }
        holder.getView<LinearLayout>(R.id.mLySegments).apply {
            removeAllViews()
            for (segment in item.segments) {
                val param = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val segmentView = SegmentsView(holder.itemView.context)
                holder.getView<LinearLayout>(R.id.mLySegments).addView(segmentView, param)
                segmentView.setData(segment)
            }
        }

    }
}