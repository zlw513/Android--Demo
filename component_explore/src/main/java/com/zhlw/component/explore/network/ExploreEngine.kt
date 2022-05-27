package com.zhlw.component.explore.network

import android.text.TextUtils
import android.util.Log
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.zhlw.lib.data.expore.ExploreInfo
import com.zhlw.module.common.constant.RouteConstant
import com.zhlw.module.common.network.crawler.IParseEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ExploreEngine(val callId: String) : IParseEngine {

    private val TAG = "ExploreEngine"

    private var mScope: CoroutineScope? = null

    override fun init() {
        mScope = CoroutineScope(Dispatchers.IO)
    }

    override fun release() {
        mScope?.cancel()
    }

    override fun startParse(url: String) {
        mScope?.launch {
            try {
                val resultList = mutableListOf<ExploreInfo>()
                val html = Jsoup.connect(url)
                val doc = html.get()
                val elementsRepo = doc.body().getElementsByClass(EXPLORE_NODE)
                Log.i(TAG, "elements size is ${elementsRepo.size}  doc: $doc")

                elementsRepo.forEach {
                    Log.d(TAG, "element is $it")
                    val organizationAndRepositoryName = it.getElementsByClass(EXPLORE_ORGANIZATION_AND_REPOSITORY_NODE).text().split("/")

                    if (organizationAndRepositoryName.size >= 2) {//repository recommend
                        val organizationName = organizationAndRepositoryName[0].trim()
                        val repositoryName = organizationAndRepositoryName[1].trim()

                        val repositoryDescNode = it.getElementsByClass(EXPLORE_REPOSITORY_DESC_NODE)
                        val repositoryDesc = if (repositoryDescNode.isNotEmpty()) repositoryDescNode[0].text().trim() else return@forEach

                        val repoTagElements = it.getElementsByClass(EXPLORE_REPOSITORY_TAG_NODE)

                        val tagList = mutableListOf<String>()
                        if (repoTagElements.isNotEmpty()) {
                            val tagSplitList = repoTagElements.text().split(" ")
                            tagSplitList.forEach {
                                tagList.add(it.trim())
                            }
                        }
                        val updateTime = it.getElementsByAttribute(EXPLORE_REPOSITORY_UPDATE_TIME_NODE).text()
                        val language = it.getElementsByAttribute(EXPLORE_LANGUAGE_NODE).text()

                        resultList.add(ExploreInfo(organizationName, repositoryName, repositoryDesc, tagList, updateTime, language))
                    } else {// topic
                        val organizationName = it.getElementsByClass(EXPLORE_TOPIC_ORGANIZATION_NAME_NODE).text().trim()
                        val repoName = it.getElementsByClass(EXPLORE_TOPIC_REPO_NAME_NODE).text().trim()
                        val repoDesc = it.getElementsByClass(EXPLORE_TOPIC_REPO_DESC_NODE).text().trim()
                        val imgNode = it.getElementsByClass(EXPLORE_TOPIC_REPOSITORY_IMAGEURL_NODE)
                        val imgUrl = imgNode.attr("src")
                        resultList.add(ExploreInfo(organizationName, repoName, repoDesc, mutableListOf(), "", "",imgUrl))
                    }
                }

                val elementsEvent = doc.body().getElementsByClass(EXPLORE_EVENT_NODE)
                elementsEvent.forEach {//spotlight

                    val organizationName = it.getElementsByClass(EXPLORE_SPOTLIGHT_ORGANIZATION_NODE).text().trim()
                    if (TextUtils.isEmpty(organizationName)) return@forEach

                    val imgNode = it.getElementsByClass(EXPLORE_SPOTLIGHT_REPOSITORY_IMAGEURL_NODE)
                    val imgUrl = imgNode.attr("src")

                    val repositoryName = it.getElementsByClass(EXPLORE_SPOTLIGHT_REPOSITORY_NODE).text().trim()

                    var repositoryDesc = ""
                    if (!TextUtils.isEmpty(repositoryName)) {
                        val repositoryDescNode = it.getElementsByClass(EXPLORE_SPOTLIGHT_REPOSITORY_DESC_NODE)

                        if (repositoryDescNode.isNotEmpty()) {
                            repositoryDesc = it.getElementsByClass(EXPLORE_SPOTLIGHT_REPOSITORY_DESC_NODE)[0].text().trim()
                        }

                        val exploreInfoSpot = ExploreInfo(organizationName, repositoryName, repositoryDesc, mutableListOf(), "", "", imgUrl)

                        resultList.add(exploreInfoSpot)

                    } else {//event
                        val repositoryDescNode = it.getElementsByClass(EXPLORE_SPOTLIGHT_REPOSITORY_DESC_NODE)
                        if (repositoryDescNode.isNotEmpty()) {
                            repositoryDesc = it.getElementsByClass(EXPLORE_SPOTLIGHT_REPOSITORY_DESC_NODE)[0].text().trim()
                        }

                        val exploreInfoEvent = ExploreInfo(organizationName, repositoryName, repositoryDesc, mutableListOf(), "", "", imgUrl)

                        resultList.add(exploreInfoEvent)
                    }
                }

                Log.i(TAG, "CC.sendCCResult resultList $resultList")
                CC.sendCCResult(callId, CCResult.success(RouteConstant.KEY_EXPLORE_DATA, resultList))

            } catch (throwable: Throwable) {
                Log.e(TAG, "startParse error $throwable")
                CC.sendCCResult(callId, CCResult.error(RouteConstant.KEY_EXPLORE_DATA, throwable))
            }
        }
    }

}