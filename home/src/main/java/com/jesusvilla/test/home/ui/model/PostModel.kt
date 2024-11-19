package com.jesusvilla.test.home.ui.model

import android.os.Parcelable
import com.jesusvilla.test.base.models.UserUI
import com.jesusvilla.test.database.entities.test.PostEntity
import com.jesusvilla.test.home.network.models.TestPost
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) : Parcelable

fun  List<TestPost>.mapperToUI(): List<PostModel> {
    return this.map {
        PostModel(
            userId = it.userId,
            id = it.id,
            title = it.title,
            body = it.body
        )
    }
}

fun  List<PostModel>.mapperToEntity(): List<PostEntity> {
    return this.map {
        PostEntity(
            userId = it.userId,
            id = it.id,
            title = it.title,
            body = it.body
        )
    }
}

fun  List<PostEntity>.mapperToModelUi(): List<PostModel> {
    return this.map {
        PostModel(
            userId = it.userId,
            id = it.id,
            title = it.title,
            body = it.body
        )
    }
}

fun PostEntity.toPostUI(): PostModel {
    return PostModel(
        userId = this.userId,
        id = this.id,
        title = this.title,
        body = this.body,
    )
}

fun PostModel.toPostEntity(): PostEntity {
    return PostEntity(
        userId = this.userId,
        id = this.id,
        title = this.title,
        body = this.body,
    )
}

