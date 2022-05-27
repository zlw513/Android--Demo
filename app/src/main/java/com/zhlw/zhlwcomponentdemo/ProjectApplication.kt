package com.zhlw.zhlwcomponentdemo

import android.app.Application
import com.zhlw.module.base.BaseConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * @Desc:
 * @Author: zlw
 * @Date: 2022/5/4
 */
@HiltAndroidApp
class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        BaseConfig.create(application = this)
    }

}