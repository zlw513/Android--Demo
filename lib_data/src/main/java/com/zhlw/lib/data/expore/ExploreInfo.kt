package com.zhlw.lib.data.expore

data class ExploreInfo(
    val organizationName : String,
    val repositoryName : String,
    val repositoryDesc : String,
    val repositoryTag : List<String>,
    val repositoryUpdateTime : String,
    val language : String,
    val imageUrl : String = ""
)