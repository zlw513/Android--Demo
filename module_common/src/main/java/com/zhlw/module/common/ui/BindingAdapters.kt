package com.zhlw.module.common.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.zhlw.module.common.R


@BindingAdapter("circle_image")
fun setImageSourceCircle(view : ImageView, url :String?){
    Glide.with(view)
        .load(url)
        .placeholder(R.drawable.ic_person)
        .error(R.drawable.ic_error)
        .transform(CircleCrop())
        .into(view)
}

@BindingAdapter("text_num")
fun setNumberText(view : TextView, num :Int?){
    view.setText(String.format("%s",num?:0))
}

@BindingAdapter(value = ["text_desc","text_content"],requireAll = false)
fun setTextWithDesc(view : TextView, desc : String?,content : String?){
    view.setText(String.format("%s:%s",desc?:"",content?:"未知"))
}
