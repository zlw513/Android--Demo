package com.zhlw.module.base.data

import com.zhlw.module.base.constant.NetErrorInfo

/**
 * 服务器返回错误信息
 */
class ResponseThrowable : Exception {
    var code: String
    var errMsg: String
    var throwable : Throwable ?= null

    constructor(error: NetErrorInfo, throwable: Throwable? = null) : super(throwable) {
        code = error.getKey()
        errMsg = error.getValue()
        this.throwable = throwable
    }

    constructor(errorCode: String, errorMessage: String, throwable: Throwable? = null) : super(throwable) {
        this.code = errorCode
        this.errMsg = errorMessage
    }

    override fun toString(): String {
        return "ResponseThrowable(code='$code', errMsg='$errMsg')"
    }

}