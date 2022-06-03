package com.zhlw.component.explore.viewmodel

import android.util.Log
import com.zhlw.component.explore.network.DAILY
import com.zhlw.component.explore.network.EXPLORE_HOST
import com.zhlw.component.explore.network.TRENDING_HOST
import com.zhlw.component.explore.network.TRENDING_PREFIX
import com.zhlw.component.explore.repository.ExploreRepository
import com.zhlw.component.explore.ui.fragment.ExploreFragment.Companion.SHOW_TYPE_EXPLORE
import com.zhlw.component.explore.ui.fragment.ExploreFragment.Companion.SHOW_TYPE_TRENDING
import com.zhlw.lib.data.expore.ExploreInfo
import com.zhlw.lib.data.expore.TrendingInfo
import com.zhlw.module.base.network.NetworkExceptionHandler
import com.zhlw.module.base.ui.viewmodel.BaseViewModel
import com.zhlw.module.base.utils.SingleLiveEvent
import com.zhlw.module.common.constant.RouteConstant.KEY_EXPLORE_DATA
import com.zhlw.module.common.constant.RouteConstant.KEY_TRENDING_DATA
import com.zhlw.module.common.utils.CCUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreFragmentViewModel @Inject constructor(val repository : ExploreRepository) : BaseViewModel(){

    private val TAG = "ExploreFragmentViewModel"

    private var mFetchType = SHOW_TYPE_TRENDING

    private val EXPLORE_KEY = "explore"

    private val mTrendingDataStore : MutableMap<String,MutableList<TrendingInfo>> by lazy { mutableMapOf() }

    private val mExploreDataStore : MutableMap<String,MutableList<ExploreInfo>> by lazy { mutableMapOf() }

    var mDataRange : String = DAILY

    val mTrendingDataResult : SingleLiveEvent<MutableList<TrendingInfo>> = SingleLiveEvent()

    val mExploreDataResult : SingleLiveEvent<MutableList<ExploreInfo>> = SingleLiveEvent()

    fun getUserName() = repository.getUserName()

    fun getFetchType() = mFetchType

    fun fetchData(type : String){
        if (SHOW_TYPE_EXPLORE == type){
            mFetchType = SHOW_TYPE_EXPLORE
            fetchExploreData()
        } else if (SHOW_TYPE_TRENDING == type){
            mFetchType = SHOW_TYPE_TRENDING
            fetchTrendingData()
        } else {
            Log.e(TAG,"fetchData error un support type")
        }
    }

    fun fetchTrendingData(){
        if (!mTrendingDataStore[mDataRange].isNullOrEmpty()){
            mTrendingDataResult.value = mTrendingDataStore[mDataRange]
            return
        }
        _showLoading.value = true
        val currentRange = mDataRange
        repository.fetchTrendingData(TRENDING_HOST+String.format("%s%s",TRENDING_PREFIX,currentRange)).callAsyncCallbackOnMainThread { cc, result ->
            _showLoading.value = false
            try {
                if (result.isSuccess){
                    val data = result.getDataItem<MutableList<TrendingInfo>>(KEY_TRENDING_DATA)
                    mTrendingDataResult.value = data
                    mTrendingDataStore[currentRange] = data
                } else {
                    val error = result.getDataItem<Throwable>(KEY_TRENDING_DATA)
                    throw error
                }
            } catch (throwable : Throwable){
                val exception = NetworkExceptionHandler.handleException(throwable)
                _showError.value = exception
                Log.e(TAG,"fetchTrendingData error $exception")
            }
        }
    }

    fun fetchExploreData(){
        if (!mExploreDataStore[EXPLORE_KEY].isNullOrEmpty()){
            mExploreDataResult.value = mExploreDataStore[EXPLORE_KEY]
            return
        }
        _showLoading.value = true
        repository.fetchExploreData(EXPLORE_HOST).callAsyncCallbackOnMainThread { cc, result ->
            _showLoading.value = false
            try {
                if (result.isSuccess){
                    val data = result.getDataItem<MutableList<ExploreInfo>>(KEY_EXPLORE_DATA)
                    mExploreDataResult.value = data
                    mExploreDataStore[EXPLORE_KEY] = data
                } else {
                    val error = result.getDataItem<Throwable>(KEY_EXPLORE_DATA)
                    throw error
                }
            } catch (throwable : Throwable){
                val exception = NetworkExceptionHandler.handleException(throwable)
                _showError.value = exception
                Log.e(TAG,"fetchExploreData error $exception")
            }
        }
    }

    fun setDataRange(dateRange : String){
        mDataRange = dateRange
        fetchTrendingData()
    }

    fun startLogin(){
        CCUtils.startLoginActivityForResult().callAsyncCallbackOnMainThread { cc, result ->
            if (result.isSuccess){
                fetchData(mFetchType)
            } else {
                Log.e(TAG,"登录失败")
            }
        }
    }

}