package com.am.letschat.di

import android.content.Context
import com.am.letschat.data.repository.Repository
import com.am.letschat.data.room.AppDatabase
import com.am.letschat.data.room.MobileDataDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideDataDao(db: AppDatabase) = db.mobileDataDao()

    @Singleton
    @Provides
    fun provideRepository(localDataSource: MobileDataDao) =
        Repository(localDataSource)

}