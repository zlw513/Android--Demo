package com.zhlw.module.base.ui.adapters

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

abstract class BaseAdapter<T,DB : ViewDataBinding>(layout : Int) : BaseQuickAdapter<T, BaseDataBindingHolder<DB>>(layout) {

    abstract fun operation(position:Int,data : T,itemView : View)

    override fun convert(holder: BaseDataBindingHolder<DB>, item: T) {
       operation(holder.absoluteAdapterPosition,item,holder.itemView)
    }

}