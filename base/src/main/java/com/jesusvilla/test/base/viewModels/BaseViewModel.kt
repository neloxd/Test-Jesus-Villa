package com.jesusvilla.test.base.viewModels

import android.content.Context
import androidx.annotation.CheckResult
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.constants.BaseConstants
import com.jesusvilla.test.base.lifecycle.ConsumerLiveData
import com.jesusvilla.test.base.managers.LoginManager
import com.jesusvilla.test.base.managers.UserManager
import com.jesusvilla.test.base.models.GenericDialogModel
import com.jesusvilla.test.base.models.NavigateFragmentModel
import com.jesusvilla.test.base.ui.models.CustomModalModel
import com.jesusvilla.test.base.ui.models.CustomToastModel
import com.jesusvilla.test.base.ui.models.CustomToastType
import com.jesusvilla.test.base.utils.UiText
import com.jesusvilla.test.core.network.Session
import com.jesusvilla.test.core.network.data.Resource
import com.jesusvilla.test.core.network.data.Status
import com.jesusvilla.test.di.LoginManagerQualifier
import com.jesusvilla.test.di.UserManagerQualifier
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jesús Villa on 11/03/24
 */
@Suppress("TooManyFunctions")
abstract class BaseViewModel : ViewModel() {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    @Inject
    @LoginManagerQualifier
    lateinit var loginManager: LoginManager

    @Inject
    @UserManagerQualifier
    lateinit var userManager: UserManager

    companion object {
        private const val MINIMUM_ACCOUNT_ID_VALID = BaseConstants.ONE
        private const val MINIMUM_DEVICE_ID_VALID = BaseConstants.ZERO
    }

    //region livedata
    protected val showCustomToast = ConsumerLiveData<CustomToastModel>()

    protected val showErrorModal = ConsumerLiveData<CustomModalModel>()

    protected val showInfoModal = ConsumerLiveData<CustomModalModel>()

    protected val _navigateFragment = ConsumerLiveData<NavigateFragmentModel>()
    val navigateFragment: LiveData<NavigateFragmentModel> get() = _navigateFragment

    protected val _navigateGraph = ConsumerLiveData<NavigateFragmentModel>()
    val navigateGraph: LiveData<NavigateFragmentModel> get() = _navigateGraph

    protected val _showGenericDialog = ConsumerLiveData<GenericDialogModel>()
    val showGenericDialog: ConsumerLiveData<GenericDialogModel> get() = _showGenericDialog

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected val _showErrorDialog = ConsumerLiveData<UiText>()

    protected val _showNotificationToast = ConsumerLiveData<UiText>()
    val showNotificationToast: LiveData<UiText> get() = _showNotificationToast

    protected val isSavingInfo = MutableLiveData(false)

    protected val isConsultingInfo = MutableLiveData(false)

    //endregion
    private inline fun <T> withLoginManager(block: (LoginManager) -> T): T? {
        return loginManager?.let(block)
    }

    fun isTokenEnable(): Boolean {
        return withLoginManager { it.isTokenEnable() } ?: false
    }

    fun isBiometricActive(): Boolean {
        return withLoginManager { it.isBiometricTokenEnable() } ?: false
    }

    fun isLogged(): Boolean {
        val accountId = loginManager.getAccountId()
        val deviceId = loginManager.getDeviceId()
        return if (
            (
                accountId < MINIMUM_ACCOUNT_ID_VALID &&
                    deviceId > MINIMUM_DEVICE_ID_VALID
                ) || loginManager.hasNotSession()
        ) {
            false
        } else {
            loginManager.setSession()
            true
        }
    }

    open fun navigate(navigateModel: NavigateFragmentModel) {
        _navigateFragment.postValue(navigateModel)
    }

    fun notifyError(errorMsg: String, title: String?) {
        val newTitle = title ?: appContext.getString(R.string.modal_title)
        showAttentionService(title = newTitle, body = errorMsg)
    }

    fun showAttentionService(
        title: String? = appContext.getString(R.string.modal_title),
        body: String,
        btnPrimaryText: String = appContext.getString(R.string.modal_btn_text)
    ) {
        showErrorModal.postValue(
            CustomModalModel
                .Builder()
                .title(title)
                .body(body)
                .icon(com.jesusvilla.test.designsystem.R.drawable.ic_warning)
                .primaryButton(
                    label = btnPrimaryText,
                    textColor = com.jesusvilla.test.designsystem.R.color.white,
                    backGroundColor = com.jesusvilla.test.designsystem.R.color.black,
                    onClick = null,
                    isVisible = true,
                )
                .build()
        )
    }

    fun showInfoModal(customModalModel: CustomModalModel) {
        showInfoModal.postValue(
            CustomModalModel
                .Builder()
                .title(customModalModel.title)
                .body(customModalModel.body)
                .icon(customModalModel.iconModal)
                .primaryButton(customModalModel.modalOptions.primaryButton)
                .secondaryButton(customModalModel.modalOptions.secondaryButton)
                .build()
        )
    }

    fun showToast(message: String, type: CustomToastType = CustomToastType.ERROR) {
        showCustomToast.postValue(
            CustomToastModel(
                text = message,
                type = type
            )
        )
    }

    /**
     * The launcher method execute viewModelsScope,
     * and then processing the method
     *
     * @param invoke: It is suspend function
     * @param navigateModel: It is the model to navigate
     * @param responseResult: Return de result from invoke proccess
     * @param errorResponse: Return de error from invoke proccess
     *
     */
    protected fun <T> launcher(
        invoke: suspend () -> Resource<T>,
        navigateModel: NavigateFragmentModel? = null,
        responseResult: ((T) -> Unit)? = null,
        errorResponse: ((Pair<String, Boolean>) -> Unit)? = null,
        showMessageError: Boolean? = false,
        showLoading: Boolean = true
    ) {
        if (!Session.isOnline) {
            notifyError(
                appContext.getString(R.string.modal_message_error_internet),
                appContext.getString(R.string.modal_title_error_internet)
            )
            return
        }

        viewModelScope.launch {
            if (showLoading) _isLoading.postValue(true)
            val response = invoke()
            if (showLoading) _isLoading.postValue(false)
            if (response.status == Status.SUCCESS) {
                navigateModel?.apply {
                    if (response.data != null && response.data != Unit) {
                        bundle = bundleOf("KEY" to response.data)
                    }
                }
                navigateModel?.let {
                    _navigateFragment.postValue(it)
                } ?: run {
                    responseResult?.invoke(response.data!!)
                }
            } else {
                response.message?.let { error ->
                    errorResponse?.let { callBackError ->
                        showMessageError?.let {
                            if (it) {
                                notifyError(response.message.toString(), response.title)
                            }
                        }
                        callBackError(Pair(error, response.isNetworkRelated))
                    } ?: notifyError(error, response.title)
                }
            }
        }
    }

    @CheckResult
    fun onShowCustomToast(): LiveData<CustomToastModel> = showCustomToast

    //region tokenDailogFragment
    @CheckResult
    fun onShowGenericDialogChange(): LiveData<GenericDialogModel> = showGenericDialog

    @CheckResult
    fun onIsLoading(): LiveData<Boolean> = isLoading

    @CheckResult
    fun onIsSavingInfo(): LiveData<Boolean> = isSavingInfo

    @CheckResult
    fun onIsConsultingInfo(): LiveData<Boolean> = isConsultingInfo

    @CheckResult
    fun onShowErrorModal(): LiveData<CustomModalModel> = showErrorModal
    fun onShowInfoModal(): LiveData<CustomModalModel> = showInfoModal
}
