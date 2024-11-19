package com.jesusvilla.test.di

import android.content.Context
import com.google.gson.Gson
import com.jesusvilla.test.base.managers.LoginManager
import com.jesusvilla.test.base.managers.UserManager
import com.jesusvilla.test.base.models.UserModel
import com.jesusvilla.test.base.preferences.PasePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    @Singleton
    @PreferencesQualifier
    fun providePasePreferences(@ApplicationContext applicationContext: Context): PasePreferences {
        return PasePreferences(applicationContext)
    }

    @Provides
    @Singleton
    @LoginManagerQualifier
    fun provideLoginManagaer(@PreferencesQualifier pasePreference: PasePreferences): LoginManager {
        return LoginManager(pasePreference)
    }

    @Provides
    @Singleton
    @UserManagerQualifier
    fun provideUserManagaer(@PreferencesQualifier pasePreference: PasePreferences): UserManager {
        return UserManager(pasePreference)
    }

    @Provides
    @Singleton
    @UserModelQualifier
    fun provideUserModel(@PreferencesQualifier pasePreference: PasePreferences): UserModel {
        val userModelStr = pasePreference.getString(UserManager.USER_KEY, "")
        return Gson().fromJson(userModelStr, UserModel::class.java)
    }
}
