package com.ilkeryildirim.soccerleague.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineScopeModule {

    @Provides
    @Singleton
    fun provideCoroutineIO() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideCoroutineMain() = Dispatchers.Main

    @Provides
    @Singleton
    fun provideMainCoroutineScope(mainDispatchers: MainCoroutineDispatcher): CoroutineScope =
        CoroutineScope(Job() + mainDispatchers)

}