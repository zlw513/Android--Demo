package com.zhlw.component.explore.repository

import com.billy.cc.core.component.CC
import com.zhlw.module.common.constant.RouteConstant
import javax.inject.Inject

class ExploreRemoteDataSource @Inject constructor(){

    fun fetchTrendingData(url : String) = CC.obtainBuilder(RouteConstant.EXPLORE_COMPONENT)
        .addParam(RouteConstant.PARAM_TRENDING_URL,url)
        .setActionName(RouteConstant.GET_TRENDING_DATA)
        .build()

    fun fetchExploreData(url : String) = CC.obtainBuilder(RouteConstant.EXPLORE_COMPONENT)
        .addParam(RouteConstant.PARAM_EXPLORE_URL,url)
        .setActionName(RouteConstant.GET_EXPLORE_DATA)
        .build()

}