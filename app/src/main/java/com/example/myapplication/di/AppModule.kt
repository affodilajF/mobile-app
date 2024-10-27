package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.local.dao.PersonDao
import com.example.myapplication.data.local.database.PersonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePersonDatabase(appContext: Application): PersonDatabase {
        return Room.databaseBuilder(
            appContext,
            PersonDatabase::class.java, "app_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePersonDao(database: PersonDatabase): PersonDao {
        return database.personDao()
    }

}