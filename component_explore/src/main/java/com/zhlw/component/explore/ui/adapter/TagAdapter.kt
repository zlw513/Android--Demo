package com.zhlw.component.explore.ui.adapter

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zhlw.component.explore.databinding.ItemTagBinding
import com.zhlw.module.base.ui.adapters.BaseAdapter

class TagAdapter(layoutId : Int) : BaseAdapter<String,ItemTagBinding>(layoutId){

    override fun operation(position: Int, data: String, itemView: View) {

    }

    override fun convert(holder: BaseDataBindingHolder<ItemTagBinding>, item: String) {
        super.convert(holder, item)
        holder.dataBinding?.data = item
        holder.dataBinding?.executePendingBindings()
    }


}