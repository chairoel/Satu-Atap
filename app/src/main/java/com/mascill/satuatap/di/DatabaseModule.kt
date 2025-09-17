package com.mascill.satuatap.di

import android.content.Context
import androidx.room.Room
import com.mascill.satuatap.data.local.SatuAtapDatabase
import com.mascill.satuatap.data.local.dao.UserDao
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SatuAtapDatabase {
        return Room.databaseBuilder(
            context,
            SatuAtapDatabase::class.java,
            SatuAtapDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(database: SatuAtapDatabase): UserDao {
        return database.userDao()
    }
}