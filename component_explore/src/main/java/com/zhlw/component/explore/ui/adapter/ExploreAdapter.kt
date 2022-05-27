package com.zhlw.component.explore.ui.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zhlw.component.explore.R
import com.zhlw.component.explore.databinding.ItemExploreBinding
import com.zhlw.lib.data.expore.ExploreInfo
import com.zhlw.module.base.ui.adapters.BaseAdapter

class ExploreAdapter(layoutId : Int) : BaseAdapter<ExploreInfo, ItemExploreBinding>(layoutId){

    override fun operation(position: Int, data: ExploreInfo, itemView: View) {
        val tagList = data.repositoryTag
        val rv_tag = itemView.findViewById<RecyclerView>(R.id.rv_explore_tag)
        if (tagList.isNotEmpty()){
            val layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            rv_tag.layoutManager = layoutManager
            val adapter = TagAdapter(R.layout.item_tag)
            adapter.data = tagList.toMutableList()
            rv_tag.adapter = adapter
            rv_tag.visibility = View.VISIBLE
        } else {
            rv_tag.visibility = View.GONE
        }
    }

    override fun convert(holder: BaseDataBindingHolder<ItemExploreBinding>, item: ExploreInfo) {
        super.convert(holder, item)
        holder.dataBinding?.data = item
        holder.dataBinding?.executePendingBindings()
    }

}