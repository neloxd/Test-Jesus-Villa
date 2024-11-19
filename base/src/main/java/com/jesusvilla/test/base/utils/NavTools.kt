package com.jesusvilla.test.base.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/**
 * Created by Jesús Villa on 22/12/23
 */
fun NavController.safeNavigate(
    actionId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null
) {
    currentDestination?.getAction(actionId)?.run { navigate(actionId, bundle, navOptions) }
}

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}
