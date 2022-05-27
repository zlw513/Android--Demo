package com.zhlw.component.mine.repository

import javax.inject.Inject

class MineRepository @Inject constructor(
    val remoteDataSource: MineRemoteDataSource,
    val localDataSource: MineLocalDataSource
){

    fun getUserName() = localDataSource.getUserName()

    fun storeUserId(id : Long) = localDataSource.storeUserId(id)

    suspend fun fetchUserInfo(username : String) = remoteDataSource.fetchUserInfo(username)

    suspend fun fetchUserRepository(username : String) = remoteDataSource.fetchUserRepository(username)

}