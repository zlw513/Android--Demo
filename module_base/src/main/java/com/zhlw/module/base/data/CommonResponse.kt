package com.zhlw.module.base.data

import com.google.gson.annotations.SerializedName

/**
 *  通用的返回模板
 *  stateCode   响应状态码
 *  stateInfo   结果信息
 *  result      返回结果 list类型
 */
data class ListData<T>(
        val stateCode: String? = null,
        val stateInfo: String? = null,
        @SerializedName("data") val result: List<T>? = null
) : IResponseResult<List<T>> {

    override fun data() = result

    override fun isSuccess(): Boolean { return result != null }

    override fun stateCode(): String? = stateCode

    override fun stateInfo(): String? = stateInfo
}

/**
 *  通用的返回模板
 *  stateCode   响应状态码
 *  stateInfo   结果信息
 *  result      返回结果 一般bean
 */
data class ResponseData<T>(
        val stateCode: String? = null,
        val stateInfo: String? = null,
        @SerializedName("data") val result: T? = null
) : IResponseResult<T> {

    override fun data() = result

    override fun isSuccess(): Boolean { return result != null }

    override fun stateCode(): String? = stateCode

    override fun stateInfo(): String? = stateInfo
}


