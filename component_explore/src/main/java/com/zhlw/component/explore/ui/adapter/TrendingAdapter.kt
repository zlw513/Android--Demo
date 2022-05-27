package com.zhlw.component.explore.ui.adapter

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zhlw.component.explore.databinding.ItemTrendingBinding
import com.zhlw.lib.data.expore.TrendingInfo
import com.zhlw.module.base.ui.adapters.BaseAdapter

class TrendingAdapter(layoutId : Int) : BaseAdapter<TrendingInfo,ItemTrendingBinding>(layoutId) {

    override fun operation(position: Int, data: TrendingInfo, itemView: View) {

    }

    override fun convert(holder: BaseDataBindingHolder<ItemTrendingBinding>, item: TrendingInfo) {
        super.convert(holder, item)
        holder.dataBinding?.data = item
        holder.dataBinding?.executePendingBindings()
    }

}