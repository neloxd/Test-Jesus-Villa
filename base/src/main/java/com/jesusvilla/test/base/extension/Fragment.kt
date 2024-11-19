@file:Suppress("unused", "TooManyFunctions", "ImportOrdering")

package com.jesusvilla.test.base.extension

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.models.DeepLinkArgModel
import com.jesusvilla.test.base.models.buildQueryString
import com.jesusvilla.test.base.utils.getMyDrawable
import com.jesusvilla.test.designsystem.components.toast.MyNotificationToast
import com.jesusvilla.test.designsystem.components.toast.MyToast
import com.jesusvilla.test.designsystem.components.toast.MyToastChangeEmail
import com.jesusvilla.test.designsystem.components.toast.ToastParams
import timber.log.Timber
import com.jesusvilla.test.designsystem.R as designsSystem

fun Fragment.makeToastShort(msg: String, background: Int? = null, textColor: Int? = null) {
    val params = ToastParams(
        requireContext(),
        msg,
        Toast.LENGTH_SHORT,
        background = background,
        textColor = textColor
    )
    MyToast.show(params)
}

fun Fragment.makeToastShortBySdkVersion(msg: String) {
    var backgroundColor = 0
    var textColorMessage = 0
    var showIcon = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        backgroundColor = com.jesusvilla.test.designsystem.R.color.white
        textColorMessage = context!!.getColor(com.jesusvilla.test.designsystem.R.color.black)
    } else {
        backgroundColor = com.jesusvilla.test.designsystem.R.color.green_dark
        textColorMessage = context!!.getColor(com.jesusvilla.test.designsystem.R.color.white)
        showIcon = false
    }
    val params = ToastParams(
        requireContext(),
        msg,
        Toast.LENGTH_SHORT,
        showIcon,
        backgroundColor,
        textColorMessage
    )
    MyToast.show(params)
}

fun Fragment.makeToastLong(msg: String) {
    val params = ToastParams(requireContext(), msg, Toast.LENGTH_LONG)
    MyToast.show(params)
}

fun Fragment.makeToastCustomNotification(msg: String) {
    MyNotificationToast.show(requireContext(), msg, Toast.LENGTH_LONG)
}

fun Fragment.makeToastChangeEmail(msg: String) {
    MyToastChangeEmail.show(requireContext(), msg, Toast.LENGTH_LONG)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.hideKeyboard(view: View) {
    activity?.hideKeyboard(view)
}

fun DialogFragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun DialogFragment.forceHideKeyboard() {
    activity?.forceHideKeyboard()
}

fun Fragment.forceHideKeyboard() {
    activity?.forceHideKeyboard()
}

fun <T> Fragment.navigateWithDeepLink(
    navigationLink: String,
    vararg arguments: DeepLinkArgModel<T>
) {

    val navigationRequest = navigationLink + arguments.toList().buildQueryString()

    findNavController().navigate(navigationRequest.toUri())
}

fun Fragment.getMyDrawable(@DrawableRes resId: Int): Drawable? {
    return requireContext().getMyDrawable(resId)
}

fun Fragment.launchUrlRedirectIntent(url: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    startActivity(intent)
}

fun Fragment.getColorFromAttr(attr: Int): Int {
    val typedValue = TypedValue()
    val theme = requireContext().theme

    theme.resolveAttribute(attr, typedValue, true)

    return typedValue.data
}

fun Fragment.getVersionName(): String {
    return requireContext().getVersionName()
}

fun Fragment.launchBiometricDialog(
    title: String,
    onResult: () -> Unit,
    onError: (() -> Unit)? = null,
    onFailed: (() -> Unit)? = null
) {
    val executor = ContextCompat.getMainExecutor(requireActivity())
    val biometricPrompt = BiometricPrompt(
        this, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                onError?.invoke()
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                onResult.invoke()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFailed?.invoke()
                makeToastShort(getString(R.string.dialog_validate_token_permission_biometric_failed))
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setNegativeButtonText(getString(designsSystem.string.dialog_cancel_label))
        .build()
    biometricPrompt.authenticate(promptInfo)
}

fun Fragment.biometricActivated(): Boolean {
    val biometricManager = BiometricManager.from(requireContext())
    when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            Timber.d("App can authenticate using biometrics.")
            return true
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
            Timber.d("No biometric features available on this device.")

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
            Timber.d("Biometric features are currently unavailable.")

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
            Timber.d(
                "Your device doesn't have any fingerprint saved, " +
                    "please check your security settings"
            )

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED ->
            Timber.d("Biometric error security update required.")

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED ->
            Timber.d("Biometric error unsupported.")

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN ->
            Timber.d("Biometric status unknown.")
    }
    return false
}
