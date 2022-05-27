package com.zhlw.component.explore.ui.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zhlw.component.explore.R

@BindingAdapter(value = ["organization_name","repository_name"],requireAll = false)
fun setTextRepoAndOrganizationName(view : TextView,organizationName : String,repositoryName : String){
    view.text = String.format("$organizationName/$repositoryName")
}

@SuppressLint("SetTextI18n")
@BindingAdapter("update_time")
fun setTextRepoUpdateTime(view : TextView,updateTime : String){
    if (!TextUtils.isEmpty(updateTime)){
        view.text = "Update On $updateTime"
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("img_url")
fun setImageViewByUrl(view : ImageView,url : String){
    if (TextUtils.isEmpty(url)) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        Glide.with(view)
            .load(url)
            .placeholder(R.drawable.github_bg)
            .error(R.drawable.github_bg)
            .into(view)
    }
}

@BindingAdapter("language")
fun setTextLanguage(view : TextView,language : String){
    if (TextUtils.isEmpty(language)){
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        view.text = language
    }
}

@BindingAdapter("repository_desc")
fun setTextRepositoryDesc(view : TextView,desc : String){
    if (TextUtils.isEmpty(desc)){
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        view.text = desc
    }
}
