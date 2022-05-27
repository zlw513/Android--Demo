package com.zhlw.module.base.network

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import com.zhlw.module.base.constant.Code_UnknownException
import com.zhlw.module.base.constant.NetErrorInfo
import com.zhlw.module.base.data.ResponseThrowable
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException


object NetworkExceptionHandler {

    fun handleException(throwable: Throwable): ResponseThrowable =
            when (throwable) {
                is JsonParseException ->
                    ResponseThrowable(error = NetErrorInfo.JSON_PARSE_ERROR, throwable = throwable)

                is JSONException ->
                    ResponseThrowable(error = NetErrorInfo.JSON_ERROR, throwable = throwable)

                is ParseException ->
                    ResponseThrowable(error = NetErrorInfo.PARSE_ERROR, throwable = throwable)

                is MalformedJsonException ->
                    ResponseThrowable(error = NetErrorInfo.MALFORMED_ERROR, throwable = throwable)

                is ConnectException ->
                    ResponseThrowable(error = NetErrorInfo.CONNECT_ERROR, throwable = throwable)

                is HttpException ->
                    ResponseThrowable(errorCode = "${throwable.code()}", errorMessage = throwable.message(), throwable = throwable)

                is SSLException ->
                    ResponseThrowable(error = NetErrorInfo.SSL_ERROR, throwable = throwable)

                is SocketTimeoutException ->
                    ResponseThrowable(error = NetErrorInfo.TIME_OUT_ERROR, throwable = throwable)

                is UnknownHostException ->
                    ResponseThrowable(error = NetErrorInfo.UNKNOWN_HOST_ERROR, throwable = throwable)

                else ->
                    ResponseThrowable(errorCode = Code_UnknownException, errorMessage = throwable.toString(), throwable = throwable)
            }

}