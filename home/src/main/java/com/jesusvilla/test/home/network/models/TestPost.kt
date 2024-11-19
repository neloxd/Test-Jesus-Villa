package com.jesusvilla.test.home.network.models

import com.google.gson.annotations.SerializedName

data class TestPost(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
)

/**
 * "userId": 1,
 *     "id": 1,
 *     "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
 *     "body": "
 */