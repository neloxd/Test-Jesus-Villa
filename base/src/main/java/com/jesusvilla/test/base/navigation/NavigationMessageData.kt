package com.jesusvilla.test.base.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigationMessageData (
    val title : String? = null,
    val message : String? = null
):Parcelable

@Parcelize
sealed class NavigationResult :Parcelable {
    @Parcelize
    data class Success(val data: NavigationMessageData) : NavigationResult(),Parcelable
    @Parcelize
    data class Error(val errorData: NavigationMessageData) : NavigationResult() , Parcelable
}