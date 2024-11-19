package com.jesusvilla.test.base.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.jesusvilla.test.base.models.NavigateFragmentModel
import com.jesusvilla.test.base.ui.BaseDeepLink.Companion.AUTHORIZATION
import com.jesusvilla.test.base.ui.BaseDeepLink.Companion.CHANNEL_MERCADOAPGO
import com.jesusvilla.test.base.ui.BaseDeepLink.Companion.CHANNEL_PAYPAL
import com.jesusvilla.test.base.ui.BaseDeepLink.Companion.MESSAGE
import com.jesusvilla.test.base.viewModels.BaseViewModel
import com.jesusvilla.test.core.BuildConfig
import com.jesusvilla.test.core.network.AuthenticationListener
import com.jesusvilla.test.core.network.Session
import timber.log.Timber

@Suppress("TooManyFunctions")
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    // region override region
    abstract fun getBaseViewModel(): BaseViewModel

    abstract fun getViewBinding(): VB

    protected fun setupObservers() {
        getBaseViewModel().navigateGraph.observe(this, ::navigateGraph)
    }

    abstract fun setupNavigationListener()
    abstract fun showHideToolbar(visible: Boolean)

    abstract fun showHideBottomNavigation(visible: Boolean)

    abstract fun setToolbarColor(color: Int)

    abstract fun setToolbarTitle(title: String)

    abstract fun initNavController(force: Boolean, itemSelected: Int? = null)

    abstract fun setToolbarMenu(data: Pair<MenuProvider, LifecycleOwner>)

    abstract fun setActionIcon(icon: Int, action: (() -> Unit?)? = null)

    abstract fun getAccountId(): Long

    abstract fun navigateByDest(dest: Int)

    protected fun getStartGraphDestination(homeGraphId: Int, loginGraphId: Int) =
        if (getBaseViewModel().isLogged()) {
            homeGraphId
        } else {
            loginGraphId
        }

    // temp
    abstract fun initViews()
    // endregion

    private fun navigateGraph(navigateFragmentModel: NavigateFragmentModel) {
        val nav = supportFragmentManager.primaryNavigationFragment?.findNavController()
        val inflater = nav?.navInflater
        val graph = inflater?.inflate(navigateFragmentModel.id)
        if (graph != null) {
            nav.graph = graph
        }
    }

    //region Sesion
    private fun initSessionListener() {
        Session.authenticationListener = AuthenticationListener { revokeSession() }
    }

    @Suppress("all")
    private fun revokeSession() {
        runOnUiThread {
            Timber.i("revokeSession")
            try {
                initNavController(true, -1)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
        _binding = getViewBinding()
        setContentView(_binding!!.root)
        initViews()
        setupNavigationListener()
        setupObservers()
        initNavController(true)
        initSessionListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.let {
            Timber.i("handleDeepLink -> it:${it.action}")
            val appLinkAction = it.action
            val appLinkData = it.data
            if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
                Timber.d("Manejar el llamado: %s, %s", appLinkData.toString(), appLinkData.encodedPath)
                handleDeepLinks(appLinkData)
            }
        }
    }

    /**
     * Handle DeepLinks PayPal/MercadoPago
     */
    private fun handleDeepLinks(appLinkData: Uri) {

       val fragment = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.firstOrNull{ it is BaseDeepLink}

        fragment?.let {
            if(it is BaseDeepLink) {
                when (appLinkData.encodedPath) {
                    BuildConfig.PAYPAL_BA_PATH_SUCCESS -> { //PayPal
                        it.handleAction(CHANNEL_PAYPAL, 1)
                    }
                    BuildConfig.PAYPAL_BA_PATH_FAILURE -> { //PayPal
                        it.handleAction(CHANNEL_PAYPAL, 2, appLinkData.getQueryParameter(MESSAGE))
                    }
                    BuildConfig.PAYPAL_BA_PATH_CANCEL -> { //PayPal
                        it.handleAction(CHANNEL_PAYPAL, 3)
                    }
                    BuildConfig.MERCADOPAGO_PATH_SUCCESS -> { //MercadoPago
                        it.handleAction(CHANNEL_MERCADOAPGO, 1, appLinkData.getQueryParameter(AUTHORIZATION))
                    }
                    BuildConfig.MERCADOPAGO_PATH_FAILURE -> { //MercadoPago
                        it.handleAction(CHANNEL_MERCADOAPGO, 2, appLinkData.getQueryParameter(MESSAGE))
                    }
                    BuildConfig.MERCADOPAGO_PATH_CANCEL -> { //MercadoPago
                        it.handleAction(CHANNEL_MERCADOAPGO, 3)
                    }
                }
            }
        }
    }
}
