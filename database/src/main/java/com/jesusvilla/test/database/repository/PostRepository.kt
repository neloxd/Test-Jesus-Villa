package com.jesusvilla.test.database.repository

import androidx.annotation.WorkerThread
import com.jesusvilla.test.database.daos.PostDao
import com.jesusvilla.test.database.di.qualifiers.PostDaoQualifier
import com.jesusvilla.test.database.entities.test.PostEntity
import com.jesusvilla.test.database.resources.DataBaseResource
import javax.inject.Inject

class PostRepository @Inject constructor(
    @PostDaoQualifier private val postDao: PostDao,
)  {
    @WorkerThread
    suspend fun getListPosts(): DataBaseResource<List<PostEntity>> {
        return try {
            DataBaseResource.Success(postDao.getPosts())
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }

    @WorkerThread
    suspend fun savePosts(list: List<PostEntity>): DataBaseResource<List<Long>> {
        return try {
            DataBaseResource.Success(postDao.insertPosts(list))
        } catch (e: Throwable) {
            DataBaseResource.Error(e)
        }
    }
}