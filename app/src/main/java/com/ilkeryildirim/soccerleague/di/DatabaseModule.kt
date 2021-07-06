package com.ilkeryildirim.soccerleague.di

import android.content.Context
import androidx.room.Room
import com.ilkeryildirim.soccerleague.data.local.AppDatabase
import com.ilkeryildirim.soccerleague.data.local.SoccerLeagueDao
import com.ilkeryildirim.soccerleague.data.local.TypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context, typeConverter: TypeConverter): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).addTypeConverter(typeConverter).build()
    }

    @Provides
   fun provideTypeConverter():TypeConverter{
        return TypeConverter()
    }



    @Provides
    fun provideSoccerLeagueDao(appDatabase: AppDatabase): SoccerLeagueDao {
        return appDatabase.soccerLeagueDao()
    }
}