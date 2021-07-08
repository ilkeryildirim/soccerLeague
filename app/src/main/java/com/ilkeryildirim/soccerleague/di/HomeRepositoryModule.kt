package com.ilkeryildirim.soccerleague.di

import com.ilkeryildirim.soccerleague.data.remote.repository.HomeDataRepository
import com.ilkeryildirim.soccerleague.data.remote.repository.HomeDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {
    @Binds
    abstract fun bindHomeDataRepository(
            homeDataRepositoryImpl: HomeDataRepositoryImpl
    ): HomeDataRepository
}