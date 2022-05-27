package com.zhlw.lib.data.expore

data class TrendingInfo(
    val organizationName : String,
    val repositoryName : String,
    val repositoryDesc : String,
    val starCount : String,
    val forkCount : String,
    val todayStar : String,
    val language : String
)