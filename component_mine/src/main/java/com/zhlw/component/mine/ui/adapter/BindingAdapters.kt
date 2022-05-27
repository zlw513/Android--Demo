package com.zhlw.component.mine.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("focus_count")
fun setFocusCount(view: TextView, count : Int){
    view.text = String.format("关注:%s",count)
}

@BindingAdapter("fans_count")
fun setFansCount(view: TextView, count : Int){
    view.text = String.format("粉丝:%s",count)
}

@BindingAdapter("user_email")
fun setUserEmail(view: TextView, email_address : String?){
    view.text = String.format("email:%s",email_address?:"未填写")
}

@BindingAdapter("user_company")
fun setUserCompany(view: TextView, company : String?){
    view.text = String.format("公司:%s",company?:"未设置")
}