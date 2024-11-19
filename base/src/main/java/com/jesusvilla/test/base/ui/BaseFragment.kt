package com.jesusvilla.test.base.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
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
import androidx.core.view.MenuProvider
import androidx.core.view.WindowCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.actionbar.ActionBarModel
import com.jesusvilla.test.base.constants.BaseConstants.EMPTY_TAG
import com.jesusvilla.test.base.constants.BaseConstants.HUNDRED_LONG
import com.jesusvilla.test.base.constants.BaseConstants.MAX_LENGTH
import com.jesusvilla.test.base.constants.BaseConstants.ONE
import com.jesusvilla.test.base.constants.BaseConstants.POINT_FIVE
import com.jesusvilla.test.base.constants.BaseConstants.POINT_FIVE_HUNDRED_AND_EIGHTY_SEVEN
import com.jesusvilla.test.base.constants.BaseConstants.POINT_ONE_HUNDRED_FOURTEEN
import com.jesusvilla.test.base.constants.BaseConstants.POINT_TWO_HUNDRED_NINETY_NINE
import com.jesusvilla.test.base.constants.BaseConstants.ZERO
import com.jesusvilla.test.base.extension.forceHideKeyboard
import com.jesusvilla.test.base.extension.hideKeyboard
import com.jesusvilla.test.base.extension.makeToastLong
import com.jesusvilla.test.base.models.GenericDialogModel
import com.jesusvilla.test.base.models.NavigateFragmentModel
import com.jesusvilla.test.base.models.ProgressDialogModel
import com.jesusvilla.test.base.navigation.NavigationResult
import com.jesusvilla.test.base.preferences.Constants.EMPTY_STRING
import com.jesusvilla.test.base.ui.models.CustomModalModel
import com.jesusvilla.test.base.ui.models.CustomToastModel
import com.jesusvilla.test.base.ui.models.CustomToastType
import com.jesusvilla.test.base.ui.models.ModalButtonModel
import com.jesusvilla.test.base.utils.UiText
import com.jesusvilla.test.base.utils.safeNavigate
import com.jesusvilla.test.base.viewModels.BaseViewModel
import com.jesusvilla.test.designsystem.components.components.CustomPaseHeader
import com.jesusvilla.test.designsystem.databinding.ActionBarBinding
import com.jesusvilla.test.designsystem.databinding.CustomToastLayoutBinding

/**
 * Created by Jesús Villa on 10/03/24
 */
@Suppress("all")
abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {

    companion object {
        const val POPBACKSTACK = "#POPBACKSTACK"
    }

    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract fun getBaseViewModel(): BaseViewModel?

    private var progressDialog: ProgressDialogFragment? = null

    private var dialog: GenericDialogFragment? = null

    private var errorModal: ModalCustomFragment? = null

    private var infoModal: ModalCustomFragment? = null

    protected val accountId: Long by lazy {
        (requireActivity() as BaseActivity<*>).getAccountId()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    protected fun navigate(action: Int, args: Bundle? = null) {
        findNavController().safeNavigate(action, args)
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

    protected fun showBottomSheetDialog(bottomSheetDialog: BottomSheetDialogFragment, tag: String) {
        forceHideKeyboard()
        binding.root.postDelayed({
            bottomSheetDialog.show(
                childFragmentManager, tag
            )
        }, HUNDRED_LONG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
        setUpObservers()
        showOrHideToolbar()
        showOrHideBottomNavBar(false)
    }

    open fun setupListeners() {
        // Setup Listeners Function
    }

    open fun setupUI() {
        setStatusBarColorAndAppearance()
        showOrHideToolbar()
        setToolbarColor()
        setActionIcon()
        showBackStackMessage()
    }

    open fun setUpObservers() {
        getBaseViewModel()?.run {
            isLoading.observe(viewLifecycleOwner, ::showProgressDialog)
            navigateFragment.observe(viewLifecycleOwner, ::navigateFragment)
            showGenericDialog.observe(viewLifecycleOwner, ::showGenericDialog)
            showNotificationToast.observe(viewLifecycleOwner, ::showNotificationToast)
            onShowCustomToast().observe(viewLifecycleOwner, ::showCustomToast)
            onShowErrorModal().observe(viewLifecycleOwner, ::showErrorModal)
            onShowInfoModal().observe(viewLifecycleOwner, ::showInformationModal)
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

    protected fun showBackStackMessage() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<NavigationResult>(
            POPBACKSTACK
        )?.observe(viewLifecycleOwner) {
            when (it) {
                is NavigationResult.Success -> {
                    getBaseViewModel()?.showToast(
                        message = it.data.message?:"",
                        type = CustomToastType.SUCCESS
                    )
                }
                is NavigationResult.Error -> {
                  showErrorModal(
                      CustomModalModel(
                          title = it.errorData.title,
                          body = it.errorData.message,
                          modalOptions = CustomModalModel.ModalOptions(
                              primaryButton = ModalButtonModel(
                                  label = requireContext().getString(R.string.modal_title),
                                  textColor = com.jesusvilla.test.designsystem.R.color.white,
                                  backGroundColor = com.jesusvilla.test.designsystem.R.color.black,
                                  onClick = null,
                                  isVisible = true
                              )
                          )
                      )
                  )
                }
            }
        }
    }

    protected fun showProgressDialog(isLoading: Boolean, message: String? = null) {
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment.newInstance(
                ProgressDialogModel(
                    message = message
                        ?: getString(com.jesusvilla.test.base.R.string.progress_dialog_generic_message)
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

    protected fun showOrHideToolbar(visible: Boolean = false) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showHideToolbar(visible)
        }
    }

    protected fun setToolbarColor(
        color: Int =
            requireContext()
                .getColor(
                    com.jesusvilla.test.designsystem.R.color.color_white
                )
    ) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).setToolbarColor(color)
        }
    }

    protected fun setToolbarTitle(title: String = EMPTY_STRING) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).setToolbarTitle(title)
        }
    }

    protected fun setToolbarMenu(data: Pair<MenuProvider, LifecycleOwner>) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).setToolbarMenu(data)
        }
    }

    protected fun setActionIcon(icon: Int = -1, action: (() -> Unit?)? = null) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).setActionIcon(icon, action)
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

    protected fun showOrHideBottomNavBar(visible: Boolean = true) {
        if (activity is BaseActivity<*>)
            (activity as BaseActivity<*>).showHideBottomNavigation(visible)
    }

    protected fun setStatusBarColorAndAppearance(
        color: Int =
            requireContext()
                .getColor(
                    com.jesusvilla.test.designsystem.R.color.color_white
                )
    ) {
        requireActivity().window.apply {
            statusBarColor = color
            changeIconsStatusBar(color)
        }
    }

    protected fun changeIconsStatusBar(color: Int) {
        requireActivity().window.apply {
            val win = this
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val controller = WindowCompat.getInsetsController(win, win.decorView)
                controller.isAppearanceLightStatusBars = color == Color.WHITE
            } else if (!com.jesusvilla.test.base.extension.isColorDark(color)) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = 0
            }
        }
    }

    protected fun newChangeIconsStatusBar(color: Int = Color.WHITE) {
        requireActivity().window.apply {
            val win = this
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val controller = WindowCompat.getInsetsController(win, win.decorView)
                controller.isAppearanceLightStatusBars = true
            } else if (!isDarkModeUI()) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = 0
            }
        }
    }

    private fun isDarkModeUI(): Boolean {
        val nightModeFlags = requireActivity().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        return when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
    }

    private fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            ONE - (
                    POINT_TWO_HUNDRED_NINETY_NINE *
                            Color.red(color) +
                            POINT_FIVE_HUNDRED_AND_EIGHTY_SEVEN *
                            Color.green(
                                color
                            ) + POINT_ONE_HUNDRED_FOURTEEN * Color.blue(color)
                    ) / MAX_LENGTH
        return darkness >= POINT_FIVE
    }

    protected fun changeStatusBarStyle(
        isDarkStatusBar: Boolean = true
    ) {
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


    protected fun popBackStackWithMessage(result: NavigationResult) {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(
            POPBACKSTACK,
            result
        )
        navController.popBackStack()
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

    private fun showErrorModal(customModalModel: CustomModalModel) {
        if (errorModal == null) {
            errorModal = ModalCustomFragment(customModalModel)
        } else {
            errorModal!!.updateData(customModalModel)
        }
        errorModal!!.show(childFragmentManager, EMPTY_TAG)
    }

    private fun showInformationModal(customModalModel: CustomModalModel) {
        if (infoModal == null) {
            infoModal = ModalCustomFragment(customModalModel)
        } else {
            infoModal!!.updateData(customModalModel)
        }
        infoModal!!.show(childFragmentManager, EMPTY_TAG)
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

    @Suppress("all")
    protected fun invokeDial(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(
            Uri.parse(
                getString(
                    com.jesusvilla.test.designsystem.R.string.domiciliation_other_bank_tel,
                    phone
                )
            )
        )
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            activity?.makeToastLong("No se pudo ejecutar la acción")
        } catch (e: Exception) {
            activity?.makeToastLong("No tiene permisos para realizar llamadas")
        }
    }

    protected fun navigateByDest(dest: Int) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).navigateByDest(dest)
        }
    }
}
