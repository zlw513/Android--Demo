package com.zhlw.component.explore.ui

import android.content.Context
import com.zhlw.component.explore.R
import razerdp.basepopup.BasePopupWindow

class ExploreTypePopupWindow : BasePopupWindow{

    constructor(context: Context) : super(context){
        setContentView(R.layout.explore_type_popup_window)
    }

}