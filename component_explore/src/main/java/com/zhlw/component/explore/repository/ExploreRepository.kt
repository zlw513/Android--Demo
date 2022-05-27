package com.zhlw.component.explore.repository

import com.zhlw.module.base.utils.PreferencesUtils
import com.zhlw.module.common.constant.STORE_USERNAME_KEY
import javax.inject.Inject

class ExploreRepository @Inject constructor(val remoteDataSource: ExploreRemoteDataSource) {

    fun getUserName() : String?{
        return PreferencesUtils.getString(STORE_USERNAME_KEY)
    }

    fun fetchTrendingData(url : String) = remoteDataSource.fetchTrendingData(url)

    fun fetchExploreData(url : String) = remoteDataSource.fetchExploreData(url)

}