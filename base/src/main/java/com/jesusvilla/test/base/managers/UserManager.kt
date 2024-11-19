package com.jesusvilla.test.base.managers

import com.google.gson.Gson
import com.jesusvilla.test.base.models.UserModel
import com.jesusvilla.test.base.preferences.PasePreferences
import com.jesusvilla.test.di.PreferencesQualifier
import javax.inject.Inject

class UserManager @Inject constructor(@PreferencesQualifier private val preferences: PasePreferences) {

    companion object {
        const val USER_KEY = "USER_KEY"
    }

    fun saveUser(user: UserModel) {
        val userJson = Gson().toJson(user)
        preferences.saveString(USER_KEY, userJson)
    }
}
