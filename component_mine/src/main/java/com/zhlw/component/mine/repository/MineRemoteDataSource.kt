package com.zhlw.component.mine.repository

import com.zhlw.lib.data.mine.PublicUserInfo
import com.zhlw.lib.data.repository.RepositoryInfo
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class MineRemoteDataSource @Inject constructor(
    retrofit: Retrofit
){

    private val service : Service = retrofit.create(Service::class.java)

    suspend fun fetchUserInfo(username : String) = service.fetchUserInfo(username)

    suspend fun fetchUserRepository(username : String) = service.fetchUserRepository(username)

    interface Service{

        @GET("users/{username}")
        suspend fun fetchUserInfo(@Path("username") username : String) : PublicUserInfo

        @GET("users/{username}/repos")
        suspend fun fetchUserRepository(@Path("username") username : String) : List<RepositoryInfo>
    }

}