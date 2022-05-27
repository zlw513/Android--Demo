package com.zhlw.component.explore.util

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.zhlw.component.explore.R
import com.zhlw.component.explore.ui.ExploreTypePopupWindow
import com.zhlw.component.explore.ui.fragment.ExploreFragment.Companion.SHOW_TYPE_EXPLORE
import com.zhlw.component.explore.ui.fragment.ExploreFragment.Companion.SHOW_TYPE_TRENDING

object ExplorePopupWindow {

    fun getExplorePopupWindow(context: Context,listener : OnItemClickListener?) : ExploreTypePopupWindow {
        val explorePopupWindow = ExploreTypePopupWindow(context)

        val exploreText = explorePopupWindow.findViewById<TextView>(R.id.popup_window_explore)
        val trendingText = explorePopupWindow.findViewById<TextView>(R.id.popup_window_trending)

        trendingText.setTextColor(context.resources.getColor(R.color.color_FF1885FD))

        exploreText.setOnClickListener {
            exploreText.setTextColor(context.resources.getColor(R.color.color_FF1885FD))
            trendingText.setTextColor(context.resources.getColor(R.color.color_B3FFFFFF))
            listener?.onItemClicked(SHOW_TYPE_EXPLORE)
            explorePopupWindow.dismiss()
        }

        trendingText.setOnClickListener {
            trendingText.setTextColor(context.resources.getColor(R.color.color_FF1885FD))
            exploreText.setTextColor(context.resources.getColor(R.color.color_B3FFFFFF))
            listener?.onItemClicked(SHOW_TYPE_TRENDING)
            explorePopupWindow.dismiss()
        }

        explorePopupWindow.setOutSideDismiss(true)

        return explorePopupWindow
    }

    fun interface OnItemClickListener{
        fun onItemClicked(type : String)
    }

}