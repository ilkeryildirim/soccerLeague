package com.ilkeryildirim.soccerleague.di

import com.ilkeryildirim.soccerleague.data.remote.api.SoccerLeagueApi
import com.ilkeryildirim.soccerleague.util.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = run {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkhttpClient():OkHttpClient{
        return  OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideSoccerLeagueApi(retrofit: Retrofit): SoccerLeagueApi =
        retrofit.create(SoccerLeagueApi::class.java)

}