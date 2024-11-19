package com.jesusvilla.test.base.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.databinding.FragmentModalInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModalInformationFragment(
    private val next: () -> Unit
) : BaseBottomSheetDialog<FragmentModalInformationBinding>(FragmentModalInformationBinding::inflate) {

    companion object {
        const val TAG = "ActivationTokenBottom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasTransparent = true
    }

    override fun setupUI() {
        with(binding) {
            tvTitleModal.isVisible = true
            tvBodyContentOne.isVisible = true
            tvTitleModal.text = getString(R.string.modal_change_phone_number_title)
            tvBodyContentOne.text = getString(R.string.modal_change_phone_number_body)
            btnNext.text = getString(R.string.modal_change_phone_number_btn_next)
            btnBackToken.text = getString(R.string.modal_change_phone_number_btn_back)
        }
    }

    override fun setupListeners() {
        with(binding) {
            btnNext.setOnClickListener {
                next.invoke()
                dismiss()
            }
            btnBackToken.setOnClickListener { dismiss() }
        }
    }
}
