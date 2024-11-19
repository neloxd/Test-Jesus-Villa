package com.jesusvilla.test.database.di.module

import android.content.Context
import androidx.room.Room
import com.jesusvilla.test.database.constants.DataBaseConfig
import com.jesusvilla.test.database.daos.PostDao
import com.jesusvilla.test.database.database.DataBase
import com.jesusvilla.test.database.di.qualifiers.PostDaoQualifier
import com.jesusvilla.test.database.di.qualifiers.PostRepositoryQualifier
import com.jesusvilla.test.database.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            DataBaseConfig.dataBaseName
        ).build()
    }

    @Provides
    @Singleton
    @PostDaoQualifier
    fun providePostsDao(dataBase: DataBase) = dataBase.postDao()

    @Provides
    @Singleton
    @PostRepositoryQualifier
    fun providePostRepository(
        @PostDaoQualifier postDao: PostDao,
    ): PostRepository {
        return PostRepository(
            postDao
        )
    }
}
