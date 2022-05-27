package com.zhlw.component.mine.ui.adapter

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zhlw.component.mine.databinding.ItemMineRepositoryBinding
import com.zhlw.lib.data.repository.RepositoryInfo
import com.zhlw.module.base.ui.adapters.BaseAdapter

class UserRepoAdapter(layoutId : Int) : BaseAdapter<RepositoryInfo,ItemMineRepositoryBinding>(layoutId) {

    override fun operation(position: Int, data: RepositoryInfo, itemView: View) {

    }

    override fun convert(holder: BaseDataBindingHolder<ItemMineRepositoryBinding>, item: RepositoryInfo) {
        super.convert(holder, item)
        holder.dataBinding?.data = item
        holder.dataBinding?.executePendingBindings()
    }


}