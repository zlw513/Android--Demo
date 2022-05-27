package com.zhlw.component.mine

import com.zhlw.component.mine.repository.MineLocalDataSource
import com.zhlw.component.mine.repository.MineRemoteDataSource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MineProvider {

    fun getMineLocalDataSource() : MineLocalDataSource

    fun getMineRemoteDataSource() : MineRemoteDataSource

}