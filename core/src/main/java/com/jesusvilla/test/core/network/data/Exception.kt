package com.jesusvilla.test.core.network.data

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

fun Exception.isNetworkRelated(): Boolean {
    return this is IOException || this is HttpException || this is JsonSyntaxException
}
