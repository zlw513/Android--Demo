package com.zhlw.component.explore.network

import android.util.Log
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.zhlw.lib.data.expore.TrendingInfo
import com.zhlw.module.common.constant.RouteConstant
import com.zhlw.module.common.network.crawler.IParseEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class TrendingEngine(val callId : String) : IParseEngine {

    private val TAG = "TrendingEngine"

    private var mScope : CoroutineScope ?= null

    override fun init() {
        mScope = CoroutineScope(Dispatchers.IO)
    }

    override fun release() {
        mScope?.cancel()
    }

    override fun startParse(url: String) {
        mScope?.launch {
            try {
                val resultList = mutableListOf<TrendingInfo>()
                val html = Jsoup.connect(url)
                val doc = html.get()
                val elements = doc.getElementsByClass(TRENDING_NODE)
                elements?.forEach {
                    val organizationAndRepositoryName = it.getElementsByClass(TRENDING_ORGANIZATION_AND_REPOSITORY_NODE).text().split("/")
                    val organizationName = organizationAndRepositoryName[0].trim()
                    val repositoryName = organizationAndRepositoryName[1].trim()
                    val repositoryDesc = it.getElementsByClass(TRENDING_REPOSITORY_DESC_NODE).text()
                    val language = it.getElementsByAttribute(TRENDING_LANGUAGE_NODE).text()
                    val starAndForkNodes = it.getElementsByClass(TRENDING_STAR_AND_FORK_NODE)
                    val starCount = starAndForkNodes[0].text().trim()
                    val forkCount = starAndForkNodes[1].text().trim()
                    val todayStar = it.getElementsByClass(TRENDING_TODAY_STAR_NODE).text().trim()

                    resultList.add(TrendingInfo(organizationName,repositoryName,repositoryDesc,starCount,forkCount,todayStar,language))
                }

                CC.sendCCResult(callId, CCResult.success(RouteConstant.KEY_TRENDING_DATA,resultList))

            } catch (throwable :Throwable){
                Log.e(TAG,"startParse error $throwable")
                CC.sendCCResult(callId, CCResult.error(RouteConstant.KEY_TRENDING_DATA,throwable))
            }
        }
    }

}