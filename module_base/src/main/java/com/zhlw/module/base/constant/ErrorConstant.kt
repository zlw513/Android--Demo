package com.zhlw.module.base.constant


const val Code_JsonParseException = "901"
const val Code_JSONException = "902"
const val Code_ParseException = "903"
const val Code_MalformedJsonException = "904"
const val Code_ConnectException = "905"
const val Code_SocketTimeoutException = "906"
const val Code_SSLException = "907"
const val Code_UnknownHostException = "908"
const val Code_UnknownException = "909"
const val Code_ResponseDataException = "910"

const val JsonParseException = "JsonParseException"
const val JSONException = "JSONException"
const val ParseException = "ParseException"
const val MalformedJsonException = "MalformedJsonException"
const val ConnectException = "ConnectException"
const val SSLException = "SSLException"
const val SocketTimeoutException = "SocketTimeoutException"
const val UnknownHostException = "UnknownHostException"
const val ResponseDataException = "响应成功，但返回数据有问题，为空或null"

enum class NetErrorInfo(private val code: String, private val err :String) {

    JSON_PARSE_ERROR(Code_JsonParseException, JsonParseException),

    JSON_ERROR(Code_JSONException, JSONException),

    PARSE_ERROR(Code_ParseException, ParseException),

    MALFORMED_ERROR(Code_MalformedJsonException, MalformedJsonException),

    CONNECT_ERROR(Code_ConnectException, ConnectException),

    TIME_OUT_ERROR(Code_SocketTimeoutException, SocketTimeoutException),

    UNKNOWN_HOST_ERROR(Code_UnknownHostException, UnknownHostException),

    RESPONSE_ERROR(Code_ResponseDataException, ResponseDataException),

    SSL_ERROR(Code_SSLException, SSLException);

    fun getValue(): String {
        return err
    }

    fun getKey(): String {
        return code
    }

}
