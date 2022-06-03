package com.zhlw.component.mine.repository

import com.zhlw.module.common.utils.PreferencesUtils
import com.zhlw.module.common.constant.STORE_USERID_KEY
import com.zhlw.module.common.constant.STORE_USERNAME_KEY
import javax.inject.Inject

class MineLocalDataSource @Inject constructor(){

    fun getUserName() = PreferencesUtils.getString(STORE_USERNAME_KEY)

    fun getUserId() = PreferencesUtils.getLong(STORE_USERID_KEY,0L)

    fun storeUserName(username : String)= PreferencesUtils.put(STORE_USERNAME_KEY,username)

    fun storeUserId(userId : Long){
        PreferencesUtils.put(STORE_USERID_KEY,userId)
    }

}