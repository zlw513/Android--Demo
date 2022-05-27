package com.zhlw.component.explore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.zhlw.component.explore.R
import com.zhlw.component.explore.network.DAILY
import com.zhlw.component.explore.network.MONTHLY
import com.zhlw.component.explore.network.WEEKLY

class TrendingHeaderView : RelativeLayout,View.OnClickListener {

    private var mClickListener : OnItemClickListener ?= null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.header_trending, this, true)
        val daily = findViewById<TextView>(R.id.trending_daily)
        val weekly = findViewById<TextView>(R.id.trending_weekly)
        val monthly = findViewById<TextView>(R.id.trending_monthly)
        daily.setOnClickListener(this)
        daily.isSelected = true
        weekly.setOnClickListener(this)
        monthly.setOnClickListener(this)
    }

    interface OnItemClickListener{
        fun onHeaderItemClicked(dateRange : String)
    }

    override fun onClick(v: View?) {
        clearItemSelected()
        when (v?.id) {
            R.id.trending_daily -> {
                mClickListener?.onHeaderItemClicked(DAILY)
                findViewById<TextView>(R.id.trending_daily).isSelected = true
            }
            R.id.trending_weekly -> {
                mClickListener?.onHeaderItemClicked(WEEKLY)
                findViewById<TextView>(R.id.trending_weekly).isSelected = true
            }
            R.id.trending_monthly -> {
                mClickListener?.onHeaderItemClicked(MONTHLY)
                findViewById<TextView>(R.id.trending_monthly).isSelected = true
            }
        }
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        mClickListener = listener
    }

    private fun clearItemSelected(){
        findViewById<TextView>(R.id.trending_daily).isSelected = false
        findViewById<TextView>(R.id.trending_weekly).isSelected = false
        findViewById<TextView>(R.id.trending_monthly).isSelected = false
    }

}