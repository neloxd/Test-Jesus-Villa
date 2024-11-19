package com.jesusvilla.test.home.ui.fragments

import androidx.core.os.bundleOf
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.jesusvilla.test.base.managers.LoginManager
import com.jesusvilla.test.base.ui.BaseFragment
import com.jesusvilla.test.base.utils.UiText
import com.jesusvilla.test.di.LoginManagerQualifier
import com.jesusvilla.test.home.R
import com.jesusvilla.test.home.databinding.FragmentTestBinding
import com.jesusvilla.test.home.ui.adapters.TestAdapter
import com.jesusvilla.test.home.ui.model.PostModel
import com.jesusvilla.test.home.viewModels.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("TooManyFunctions")
@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBinding>(FragmentTestBinding::inflate) {

    private val viewModel: TestViewModel by hiltNavGraphViewModels(R.id.home_graph)

    @Inject
    @LoginManagerQualifier
    lateinit var loginManager: LoginManager
    override fun getBaseViewModel() = viewModel

    private val adapter by lazy {
        setupAdapter()
    }

    companion object {
        const val TAG = "TestFragment"
        const val KEY_TAG_POST = "KEY_TAG_POST"
    }

    override fun setupUI() {
        super.setupUI()
        showOrHideToolbar(false)
        binding.list.adapter = adapter
    }

    init {
        showOrHideToolbar(false)
    }

    private fun setupAdapter(): TestAdapter {
        return TestAdapter(dataSet = ArrayList(), onClickListener = ::navigateTagDetail)
    }

    private fun navigateTagDetail(detailPost: PostModel) {
        findNavController().navigate(
            R.id.action_testFragment_to_testDetailFragment,
            bundleOf(KEY_TAG_POST to detailPost)
        )
    }

    override fun setUpObservers() {
        super.setUpObservers()
        with(viewModel) {
            onPosts().observe(viewLifecycleOwner) {
                adapter.addAll(it)
            }
        }
    }
}
