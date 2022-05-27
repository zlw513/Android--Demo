package com.zhlw.debug

import android.app.Application
import com.zhlw.module.base.BaseConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DebugApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        BaseConfig.create(application = this)
    }

}