package com.jesusvilla.test.base.navigation

import androidx.annotation.IdRes

fun interface NavigationHandler {
    fun navigate(@IdRes dest: Int)
}
