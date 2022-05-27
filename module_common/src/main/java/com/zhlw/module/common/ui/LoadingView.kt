package com.zhlw.module.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation.*
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.wang.avi.AVLoadingIndicatorView
import com.zhlw.module.common.R

class LoadingView : AVLoadingIndicatorView {

    constructor(context: Context) : super(context)

    constructor(context : Context,attrs :AttributeSet) : super(context,attrs)

    constructor(context : Context,attrs :AttributeSet,defStyleAttr : Int) : super(context,attrs,defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

}