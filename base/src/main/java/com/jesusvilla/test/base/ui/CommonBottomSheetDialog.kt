package com.jesusvilla.test.base.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.databinding.BottomSheetGenericDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommonBottomSheetDialog :
    BaseBottomSheetDialog<BottomSheetGenericDialogBinding>(
        BottomSheetGenericDialogBinding::inflate
    ) {

    companion object {
        const val TAG = "LocateTagBottom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasTransparent = true
    }

    override fun setupUI() {
        with(binding) {
            tvTitle.isVisible = true
            tvTitle.text = getString(R.string.fragment_locate_tag_title)
            tvSubtitle.isVisible = true
            tvSubtitle.text = getString(R.string.fragment_locate_tag_subtitle)
            btnOk.text = getString(R.string.fragment_locate_tag_understand)
        }
    }

    override fun setupListeners() {
        with(binding) {
            btnOk.setOnClickListener { dismiss() }
            ivClose.setOnClickListener { dismiss() }
        }
    }
}
