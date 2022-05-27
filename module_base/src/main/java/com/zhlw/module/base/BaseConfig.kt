package com.zhlw.module.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV

/**
 * 项目配置
 */
class BaseConfig {

    private val TAG = "BaseConfig"

    //private val isDebugMode = BuildConfig.DEBUG

    private var mAppContext : Context? = null

    fun getContext() : Context{
        return mAppContext!!
    }

    /**
     * 可以做各种库初始化
     */
    private fun initConfig(application: Application?) {
        if (application != null){
            MMKV.initialize(application)
        } else {
            Log.e(TAG,"BaseConfig 初始化异常 application is $application")
        }
    }

    companion object{

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: BaseConfig? = null

        /**
         * 当调用完create后，默认是有值的
         */
        fun getInstance() : BaseConfig {
            return instance!!
        }

        /**
         * 注：必须先在application中最优先调用该方法初始化，否则无法使用该库
         */
        fun create(application: Application?) : BaseConfig {
            instance = BaseConfig()
            instance!!.mAppContext = application
            instance!!.initConfig(application)
            return instance!!
        }

        /**
         * 释放资源
         */
        fun release(){
            instance?.mAppContext = null
        }

    }


}