package com.jesusvilla.test.base.managers

import com.google.gson.Gson
import com.jesusvilla.test.base.models.UserModel
import com.jesusvilla.test.base.preferences.PasePreferences
import com.jesusvilla.test.core.network.AuthenticationInterceptor.Companion.COOKIE_KEY
import com.jesusvilla.test.core.network.Session
import com.jesusvilla.test.di.PreferencesQualifier
import javax.inject.Inject

@Suppress("TooManyFunctions")
class LoginManager @Inject constructor(@PreferencesQualifier private val preferences: PasePreferences) {

    companion object {
        private const val ACCOUNT_ID_KEY = "ACCOUNT_ID_KEY"
        private const val DEVICE_ID_KEY = "DEVICE_ID_KEY"
        private const val ROL_KEY = "ROL_KEY"
        private const val BIOMETRIC_TOKEN_ENABLED = "BIOMETRIC_TOKEN_ENABLED"
        private const val IS_TOKEN_ENABLED = "IS_TOKEN_ENABLED"
        private const val IS_REQUEST_TOKEN_ACTIVATION_IN_HOME = "IS_REQUEST_IN_HOME"
    }

    fun saveDeviceId(deviceId: Long) {
        preferences.saveLong(DEVICE_ID_KEY, deviceId)
    }

    fun saveAccountId(accountId: Long) {
        preferences.saveLong(ACCOUNT_ID_KEY, accountId)
        preferences.saveStringSet(COOKIE_KEY, Session.session)
    }

    fun removeAccount() {
        Session.session.clear()
        preferences.removeAll()
    }

    fun getAccountId() = preferences.getLong(ACCOUNT_ID_KEY)

    fun hasNotSession() = preferences.getStringSet(COOKIE_KEY).isEmpty() ?: true

    fun setSession() {
        Session.session = (preferences.getStringSet(COOKIE_KEY) as HashSet<String>)
        val userModelStr = preferences.getString(UserManager.USER_KEY, "")
        Session.currentUser = Gson().fromJson(userModelStr, UserModel::class.java)
    }

    fun getDeviceId() = preferences.getLong(DEVICE_ID_KEY)

    fun saveRol(rol: String) {
        preferences.saveString(ROL_KEY, rol)
    }

    fun saveBiometricTokenEnable(isEnable: Boolean) {
        preferences.saveBoolean(BIOMETRIC_TOKEN_ENABLED, isEnable)
    }

    fun isBiometricTokenEnable(): Boolean {
        return preferences.getBoolean(BIOMETRIC_TOKEN_ENABLED)
    }

    fun saveTokenEnable(isEnable: Boolean) {
        preferences.saveBoolean(IS_TOKEN_ENABLED, isEnable)
    }

    fun isTokenEnable(): Boolean {
        return preferences.getBoolean(IS_TOKEN_ENABLED)
    }

    fun saveIsRequestInHome(isRequest: Boolean) {
        preferences.saveBoolean(IS_REQUEST_TOKEN_ACTIVATION_IN_HOME, isRequest)
    }

    fun isRequestInHome(): Boolean {
        return preferences.getBoolean(IS_REQUEST_TOKEN_ACTIVATION_IN_HOME)
    }
}
