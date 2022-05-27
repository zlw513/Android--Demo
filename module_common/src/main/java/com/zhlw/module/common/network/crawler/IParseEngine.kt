package com.zhlw.module.common.network.crawler

interface IParseEngine {

    fun init()

    fun release()

    fun startParse(url : String)

}