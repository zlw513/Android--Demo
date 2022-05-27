package com.zhlw.module.base.data

interface IResponseResult<T> {
    fun isSuccess() : Boolean
    fun data(): T?
    fun stateCode() : String?
    fun stateInfo() : String?
}