package com.zhlw.module.base.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.util.*

object PreferencesUtils {
    var mmkv: MMKV? = null

    init {
        mmkv = MMKV.defaultMMKV()
    }

    fun put(key: String, value: Any?): Boolean {
        return when (value) {
            is String -> mmkv?.encode(key, value)!!
            is Float -> mmkv?.encode(key, value)!!
            is Boolean -> mmkv?.encode(key, value)!!
            is Int -> mmkv?.encode(key, value)!!
            is Long -> mmkv?.encode(key, value)!!
            is Double -> mmkv?.encode(key, value)!!
            is ByteArray -> mmkv?.encode(key, value)!!
            else -> false
        }
    }


    fun <T : Parcelable> put(key: String, t: T?): Boolean {
        if (t == null) {
            return false
        }
        return mmkv?.encode(key, t)!!
    }

    fun put(key: String, sets: Set<String>?): Boolean {
        if (sets == null) {
            return false
        }
        return mmkv?.encode(key, sets)!!
    }

    fun getInt(key: String,defValue : Int): Int? {
        return mmkv?.decodeInt(key, defValue)
    }

    fun getDouble(key: String,defValue : Double): Double? {
        return mmkv?.decodeDouble(key, defValue)
    }

    fun getLong(key: String,defValue : Long): Long? {
        return mmkv?.decodeLong(key, defValue)
    }

    fun getBoolean(key: String): Boolean? {
        return mmkv?.decodeBool(key, false)
    }

    fun getBooleanDefaultTrue(key: String): Boolean? {
        return mmkv?.decodeBool(key, true)
    }

    fun getFloat(key: String,defValue : Float): Float? {
        return mmkv?.decodeFloat(key, defValue)
    }

    fun getByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun getString(key: String): String? {
        return mmkv?.decodeString(key, "")
    }

    fun getString(key: String,defValue : String): String? {
        return mmkv?.decodeString(key, defValue)
    }

    /**
     * SpUtils.getParcelable<Class>("")
     */
    inline fun <reified T : Parcelable> getParcelable(key: String): T? {
        return mmkv?.decodeParcelable(key, T::class.java)
    }

    fun getStringSet(key: String): Set<String>? {
        return mmkv?.decodeStringSet(key, Collections.emptySet())
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }

}
