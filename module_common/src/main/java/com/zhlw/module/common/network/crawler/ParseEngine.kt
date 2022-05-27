package com.zhlw.module.common.network.crawler

class ParseEngine : IParseEngine{

    private var mParseEngine : IParseEngine ?= null

    constructor(engine: IParseEngine){
        mParseEngine = engine
    }

    override fun init() {
        mParseEngine?.init()
    }

    override fun release() {
        mParseEngine?.release()
    }

    override fun startParse(url: String) {
        mParseEngine?.startParse(url)
    }

}