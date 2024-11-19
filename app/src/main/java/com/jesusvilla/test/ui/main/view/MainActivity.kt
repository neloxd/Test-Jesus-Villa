package com.jesusvilla.test.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.NavigationRes
import androidx.core.view.MenuProvider
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jesusvilla.test.R
import com.jesusvilla.test.base.models.UserModel
import com.jesusvilla.test.base.navigation.NavigationHandler
import com.jesusvilla.test.base.network.ConnectionUtil
import com.jesusvilla.test.base.ui.BaseActivity
import com.jesusvilla.test.base.viewModels.BaseViewModel
import com.jesusvilla.test.constants.Ids.HOME_GRAPH
import com.jesusvilla.test.core.network.Session
import com.jesusvilla.test.databinding.ActivityMainBinding
import com.jesusvilla.test.ui.main.viewModel.MainViewModelV1
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@Suppress("TooManyFunctions")
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationHandler {

    private lateinit var navController: NavController
    private var itemSelected = -1

    private val viewModel: MainViewModelV1 by viewModels()
    override fun getBaseViewModel(): BaseViewModel = viewModel
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun showHideToolbar(visible: Boolean) {
        binding.toolbar.isVisible = visible
    }

    private lateinit var connectUtils: ConnectionUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectUtils = ConnectionUtil(this)
        val connectionStateListener =
            ConnectionUtil.ConnectionStateListener { isAvailable -> Session.isOnline = isAvailable }
        connectUtils.onInternetStateListener(connectionStateListener)
        showHideToolbar(false)
    }

    override fun showHideBottomNavigation(visible: Boolean) {
        binding.navBar.isVisible = visible
    }

    override fun setToolbarColor(color: Int) {
        binding.toolbar.setBackgroundColor(color)
    }

    override fun setToolbarTitle(title: String) {
        binding.tvTitleToolbar.text = title
    }

    override fun setActionIcon(icon: Int, action: (() -> Unit?)?) {
        if (icon == -1) {
            binding.ivActionIcon.isGone = true
        } else {
            binding.ivActionIcon.isGone = false
            binding.ivActionIcon.setImageResource(icon)
            binding.ivActionIcon.setOnClickListener {
                action?.invoke()
            }
        }
    }

    override fun getAccountId(): Long = (Session.currentUser as UserModel).id
    private fun resetBottomNavigationSelection() {
        binding.navBar.menu.findItem(R.id.bottomNavigationHomeId)?.isChecked = true
    }

    override fun initNavController(force: Boolean, selected: Int?) {

        selected?.let {
            itemSelected = selected
            resetBottomNavigationSelection()
        }

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        navController = host.navController

        val mainGraph = navController.navInflater.inflate(R.navigation.main_graph)
        mainGraph.setStartDestination(HOME_GRAPH)

        binding.navBar.setupWithNavController(navController)

        navController.graph = mainGraph

        binding.navBar.setupWithNavControllerCustom()

        if (itemSelected != -1 && !force) {
            checkNavigate(itemSelected)
        }
    }

    override fun setToolbarMenu(data: Pair<MenuProvider, LifecycleOwner>) {
        with(binding.toolbar) {
            addMenuProvider(data.first, data.second)
        }
    }

    override fun initViews() {
        with(binding) {
            ivBackArrow.setOnClickListener { navController.navigateUp() }
        }
    }

    override fun navigate(@NavigationRes dest: Int) {
        val graph = navController.navInflater.inflate(dest)
        navController.graph = graph
    }

    override fun navigateByDest(dest: Int) {
        when (dest) {
            com.jesusvilla.test.base.R.id.dest_tag_detail -> {
                navController.navigate(
                    com.jesusvilla.test.home.R.id.home_graph,
                )
            }

            com.jesusvilla.test.base.R.id.dest_authentication -> {
            }

            com.jesusvilla.test.base.R.id.dest_home -> {
                navController.navigate(R.id.action_global_home)
            }

            else -> {
                initNavController(true, -1)
            }
        }
    }

    override fun setupNavigationListener() {
        // Seyup Navigation Listeners
    }

    private fun checkNavigate(itemId: Int): Boolean {
        return when (itemId) {
            R.id.bottomNavigationHomeId -> {
                true
            }

            R.id.bottomNavigationTripId -> {
                true
            }

            R.id.bottomNavigationAccountId -> {
                true
            }

            else -> false
        }
    }

    private fun BottomNavigationView.setupWithNavControllerCustom() {
        setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomNavigationHomeId -> {
                    navController.navigate(R.id.action_global_home)
                    return@setOnItemSelectedListener true
                }

                R.id.bottomNavigationAccountId -> {
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.i("onNewIntent")
    }
}
