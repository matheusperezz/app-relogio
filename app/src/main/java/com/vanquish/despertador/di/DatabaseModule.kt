package com.vanquish.despertador.di

import android.content.Context
import androidx.room.Room
import com.vanquish.despertador.data.AppDatabase
import com.vanquish.despertador.data.converters.Converters
import com.vanquish.despertador.data.dao.AlarmDao
import com.vanquish.despertador.data.migrations.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "db_alarms"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )   .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideConverters(): Converters {
        return Converters()
    }

    @Provides
    fun provideAlarmDao(database: AppDatabase): AlarmDao {
        return database.alarmDao()
    }
}