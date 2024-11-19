package com.jesusvilla.test.home.ui.fragments

import android.os.Build
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.jesusvilla.test.base.ui.BaseFragment
import com.jesusvilla.test.home.R
import com.jesusvilla.test.home.databinding.FragmentTestDetailBinding
import com.jesusvilla.test.home.ui.model.PostModel
import com.jesusvilla.test.home.viewModels.TestViewModel
import dagger.hilt.android.AndroidEntryPoint


@Suppress("TooManyFunctions")
@AndroidEntryPoint
class TestDetailFragment : BaseFragment<FragmentTestDetailBinding>(FragmentTestDetailBinding::inflate) {

    private val viewModel: TestViewModel by hiltNavGraphViewModels(R.id.home_graph)
    override fun getBaseViewModel() = viewModel

    private val test by lazy { getPost() }

    override fun setupUI() {
        super.setupUI()
        with(binding) {
            root.post {
                showOrHideToolbar(true)
            }
            tvTitle.text = test.title
            tvBody.text = test.body
        }
    }

    private fun getPost(): PostModel {
        val value =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(TestFragment.KEY_TAG_POST, PostModel::class.java)
        } else {
            requireArguments().getParcelable(TestFragment.KEY_TAG_POST)
        }
        return value!!
    }
}