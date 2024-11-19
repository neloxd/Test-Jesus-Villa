package com.jesusvilla.test.base.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.databinding.FragmentModalCustomBinding
import com.jesusvilla.test.base.ui.models.CustomModalModel

class ModalCustomFragment(private var customModalModel: CustomModalModel) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalCustomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateData(updateData: CustomModalModel) {
        customModalModel = updateData
        configUi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUi()
    }

    private fun configUi() {
        with(binding) {
            customModalModel.iconModal?.let {
                ivIconModal.setImageDrawable(ContextCompat.getDrawable(ivIconModal.context, it))
            }
            tvTitleModal.text = customModalModel.title
            tvBodyModal.text = customModalModel.body
            customModalModel.modalOptions.primaryButton?.let { btnModel ->
                btnModal.isVisible = btnModel.isVisible ?: true
                btnModal.backgroundTintList = ColorStateList.valueOf(
                    btnModal.context.getColor(
                        btnModel.backGroundColor ?: com.jesusvilla.test.designsystem.R.color.black
                    )
                )
                btnModal.setTextColor(
                    btnModal.context.getColor(
                        btnModel.textColor ?: com.jesusvilla.test.designsystem.R.color.white
                    )
                )
                btnModal.text = btnModel.label?: requireContext().getString(R.string.modal_title)
                btnModal.setOnClickListener {
                    btnModel.onClick?.invoke()
                    dismiss()
                }
            }
            customModalModel.modalOptions.secondaryButton?.let { btnModel ->
                btnBack.isVisible = btnModel.isVisible ?: false
                btnBack.backgroundTintList = ColorStateList.valueOf(
                    btnBack.context.getColor(
                        btnModel.backGroundColor
                            ?: com.jesusvilla.test.designsystem.R.color.gray_accent
                    )
                )
                btnBack
                    .setTextColor(
                        btnBack.context
                            .getColor(
                                btnModel.textColor ?: com.jesusvilla.test.designsystem.R.color.black
                            )
                    )
                btnBack.text = btnModel.label.toString()
                btnBack.setOnClickListener {
                    btnModel.onClick?.invoke()
                    dismiss()
                }
            }
        }
    }
}
