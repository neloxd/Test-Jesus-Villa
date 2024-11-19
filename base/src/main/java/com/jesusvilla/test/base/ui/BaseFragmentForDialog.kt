package com.jesusvilla.test.base.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.actionbar.ActionBarModel
import com.jesusvilla.test.base.constants.BaseConstants.EMPTY_TAG
import com.jesusvilla.test.base.constants.BaseConstants.ZERO
import com.jesusvilla.test.base.extension.hideKeyboard
import com.jesusvilla.test.base.extension.makeToastLong
import com.jesusvilla.test.base.models.GenericDialogModel
import com.jesusvilla.test.base.models.NavigateFragmentModel
import com.jesusvilla.test.base.models.ProgressDialogModel
import com.jesusvilla.test.base.ui.models.CustomModalModel
import com.jesusvilla.test.base.ui.models.CustomToastModel
import com.jesusvilla.test.base.ui.models.CustomToastType
import com.jesusvilla.test.base.utils.UiText
import com.jesusvilla.test.base.utils.safeNavigate
import com.jesusvilla.test.base.viewModels.BaseViewModelForDialog
import com.jesusvilla.test.designsystem.components.components.CustomPaseHeader
import com.jesusvilla.test.designsystem.databinding.ActionBarBinding
import com.jesusvilla.test.designsystem.databinding.CustomToastLayoutBinding

/**
 * Created by Jesús Villa on 10/03/24
 */
@Suppress("TooManyFunctions")
abstract class BaseFragmentForDialog<T : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
) :
    Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract fun getBaseViewModel(): BaseViewModelForDialog?

    private var progressDialog: ProgressDialogFragment? = null

    private var errorModal: ModalCustomFragment? = null

    private var dialog: GenericDialogFragment? = null

    private var modalCustom: ModalCustomFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    fun navigate(action: Int, args: Bundle? = null) {
        findNavController().navigate(action, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun setupPaseHeader(
        paseHeaderBinding: CustomPaseHeader,
        title: String? = null,
        description: String? = null,
        showLogo: Boolean = false
    ) {
        with(paseHeaderBinding) {
            setup(title, description, showLogo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setUpObservers()
        setStatusBarColorAndAppearance()
        changeStatusBarStyle()
    }

    open fun setupListeners() {
        // Setup Listeners Function
    }
    open fun setUpObservers() {
        getBaseViewModel()?.run {
            isLoading.observe(viewLifecycleOwner, ::showProgressDialog)
            navigateFragment.observe(viewLifecycleOwner, ::navigateFragment)
            showGenericDialog.observe(viewLifecycleOwner, ::showGenericDialog)
            showNotificationToast.observe(viewLifecycleOwner, ::showNotificationToast)
            onShowCustomToast().observe(viewLifecycleOwner, ::showCustomToast)
            onShowCustomModal().observe(viewLifecycleOwner, ::showCustomModal)
            onShowErrorModal().observe(viewLifecycleOwner, ::showErrorModal)
        }
    }

    protected fun showNotificationToast(message: UiText) {
        makeToastLong(message.asString(requireContext()))
    }

    protected fun showGenericDialog(genericDialogModel: GenericDialogModel) {
        if (dialog == null) {
            dialog = GenericDialogFragment(genericDialogModel)
        }

        dialog!!.show(childFragmentManager, GenericDialogFragment.TAG)
    }

    protected fun showProgressDialog(isLoading: Boolean, message: String? = null) {
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment.newInstance(
                ProgressDialogModel(
                    message = message ?: getString(R.string.progress_dialog_generic_message)
                )
            )
        }

        if (isLoading) {
            if (!progressDialog!!.isAdded) progressDialog!!.show(
                childFragmentManager, ProgressDialogFragment.PROGRESS_DIALOG_TAG
            )
        } else {
            if (progressDialog!!.isAdded) progressDialog!!.dismissNow()
        }
    }

    protected fun navigateFragment(navigateFragmentModel: NavigateFragmentModel) {
        hideKeyboard()
        findNavController().safeNavigate(
            actionId = navigateFragmentModel.id,
            bundle = navigateFragmentModel.bundle,
            navOptions = navigateFragmentModel.navOptions
        )
    }

    protected fun checkNavigate() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).initNavController(false)
    }

    protected fun setStatusBarColorAndAppearance(
        color: Int =
            requireContext()
                .getColor(
                    com.jesusvilla.test.designsystem.R.color.color_white
                )
    ) {
        requireActivity().window.statusBarColor = color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.setSystemBarsAppearance(
                0, APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION") requireActivity().window.decorView.systemUiVisibility =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
        }
    }

    protected fun changeStatusBarStyle(isDarkStatusBar: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = requireActivity().window.insetsController
            if (controller != null) {
                if (isDarkStatusBar) {
                    controller.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else {
                    controller.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = requireActivity().window.decorView
            if (isDarkStatusBar) {
                @Suppress("DEPRECATION") decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                @Suppress("DEPRECATION") decorView.systemUiVisibility =
                    decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    protected fun setupBackCallback(backActionNavigation: () -> Unit) {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            backActionNavigation()
        }
    }

    protected fun onEditorActionBase(actionId: Int, call: () -> Unit): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            call()
            true
        } else false
    }

    protected fun showCustomToast(customToastModel: CustomToastModel) {
        val customToastBinding = CustomToastLayoutBinding.inflate(layoutInflater)
        val customToast = Snackbar.make(requireView(), EMPTY_TAG, Snackbar.LENGTH_LONG)
        customToastBinding.tvToast.text = customToastModel.text

        val color = when (customToastModel.type) {
            CustomToastType.SUCCESS -> com.jesusvilla.test.designsystem.R.color.success_color_toast
            CustomToastType.ERROR -> com.jesusvilla.test.designsystem.R.color.error_color_toast
            CustomToastType.WARNING -> com.jesusvilla.test.designsystem.R.color.warning_color_toast
        }
        val icon = when (customToastModel.type) {
            CustomToastType.SUCCESS -> com.jesusvilla.test.designsystem.R.drawable.ic_check_circle_white
            CustomToastType.ERROR -> com.jesusvilla.test.designsystem.R.drawable.ic_error
            CustomToastType.WARNING -> com.jesusvilla.test.designsystem.R.drawable.ic_warning
        }
        context?.let {
            customToastBinding.rootToast.setCardBackgroundColor(it.getColor(color))
            customToastBinding.ivToast.setImageDrawable(it.getDrawable(icon))
        }
        val customToastLayout = customToast.view as ViewGroup
        customToastLayout.addView(customToastBinding.root, ZERO)
        customToastLayout.setBackgroundColor(Color.TRANSPARENT)
        val params = customToastLayout.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        customToastLayout.layoutParams = params
        customToast.show()
    }

    private fun showCustomModal(customModalModel: CustomModalModel) {
        if (modalCustom == null) {
            modalCustom = ModalCustomFragment(customModalModel)
        }
        modalCustom!!.show(childFragmentManager, EMPTY_TAG)
    }

    protected fun setupActionBarDialog(
        actionBarInclude: ActionBarBinding,
        actionBarModel: ActionBarModel? = null
    ) {
        if (actionBarModel != null) {
            actionBarModel.title?.let {
                actionBarInclude.title.text = it
            } ?: actionBarInclude.title.apply { isInvisible = true }
            actionBarModel.onBackArrow?.let {
                actionBarInclude.btnBack.setOnClickListener(it)
            } ?: actionBarInclude.btnBack.apply { isInvisible = true }
            actionBarModel.onBackArrowIcon?.let {
                actionBarInclude.btnBack.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        it
                    )
                )
            } ?: actionBarInclude.btnBack.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        com.jesusvilla.test.designsystem.R.drawable.ic_arrow_back
                    )
                )
            }
            actionBarModel.colorBack?.let {
                // Aqui el color
            } ?: actionBarInclude.btnBack.apply {
                actionBarInclude.toolbar.setBackgroundColor(
                    context.getColor(
                        com.jesusvilla.test.designsystem.R.color.white
                    )
                )
            }
        }
    }

    private fun showErrorModal(customModalModel: CustomModalModel) {
        if (errorModal == null) {
            errorModal = ModalCustomFragment(customModalModel)
        } else {
            errorModal!!.updateData(customModalModel)
        }
        errorModal!!.show(childFragmentManager, EMPTY_TAG)
    }
}
