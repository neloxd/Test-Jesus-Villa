package com.jesusvilla.test.base.viewModels

import android.content.Context
import androidx.annotation.CheckResult
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusvilla.test.base.R
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
abstract class BaseViewModelForDialog : ViewModel() {

    @Inject
    @LoginManagerQualifier
    lateinit var loginManager: LoginManager

    @Inject
    @UserManagerQualifier

    lateinit var userManager: UserManager

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    //region livedata
    protected val showCustomToast = MutableLiveData<CustomToastModel>()

    val showCustomModal = MutableLiveData<CustomModalModel>()

    protected val showErrorModal = ConsumerLiveData<CustomModalModel>()

    protected val _navigateFragment = ConsumerLiveData<NavigateFragmentModel>()
    val navigateFragment: LiveData<NavigateFragmentModel> get() = _navigateFragment

    protected val _showGenericDialog = ConsumerLiveData<GenericDialogModel>()
    val showGenericDialog: ConsumerLiveData<GenericDialogModel> get() = _showGenericDialog

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected val _showErrorDialog = ConsumerLiveData<UiText>()
    val showErrorDialog: ConsumerLiveData<UiText> get() = _showErrorDialog

    protected val _showNotificationToast = ConsumerLiveData<UiText>()
    val showNotificationToast: LiveData<UiText> get() = _showNotificationToast

    //endregion

    open fun navigate(navigateModel: NavigateFragmentModel) {
        _navigateFragment.postValue(navigateModel)
    }

    fun showToast(message: String, type: CustomToastType = CustomToastType.ERROR) {
        showCustomToast.postValue(
            CustomToastModel(
                text = message,
                type = type
            )
        )
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
                .icon(com.jesusvilla.test.designsystem.R.drawable.ic_warning)
                .body(body)
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
    @Suppress("LongParameterList")
    protected fun <T> launcher(
        invoke: suspend () -> Resource<T>,
        navigateModel: NavigateFragmentModel? = null,
        responseResult: ((T) -> Unit)? = null,
        errorResponse: ((Pair<String, Boolean>) -> Unit)? = null,
        networkError: (() -> Unit)? = null,
        showMessageError: Boolean? = false
    ) {
        if (!Session.isOnline) {
            notifyError(
                appContext.getString(R.string.modal_message_error_internet),
                appContext.getString(R.string.modal_title_error_internet)
            )
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = invoke()
            _isLoading.postValue(false)
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

    @CheckResult
    fun onShowCustomModal(): LiveData<CustomModalModel> = showCustomModal

    @CheckResult
    fun onShowErrorModal(): LiveData<CustomModalModel> = showErrorModal
}
