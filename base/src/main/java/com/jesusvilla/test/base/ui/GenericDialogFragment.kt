package com.jesusvilla.test.base.ui

import android.app.Dialog
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jesusvilla.test.base.databinding.FragmentGenericDialogBinding
import com.jesusvilla.test.base.models.GenericDialogModel
import com.jesusvilla.test.designsystem.R

class GenericDialogFragment(
    private val model: GenericDialogModel
) : DialogFragment() {
    private var _binding: FragmentGenericDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "GenericDialogFragment"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        _binding = FragmentGenericDialogBinding.inflate(layoutInflater)
        with(binding) {
            tvTitle.text = model.title?.asString(requireContext())
                ?: run { tvTitle.isVisible = false }.toString()
            tvBody.text = model.body?.asString(requireContext())
            model.dialogOptions.primaryButton?.let { btnModel ->
                btnPrimary.text = btnModel.label?.asString(requireContext())
                btnPrimary.setOnClickListener {
                    btnModel.onClick?.onClick(it)
                    dismiss()
                }
            } ?: btnPrimary.apply { isGone = true }

            model.dialogOptions.secondaryButton?.let { btnModel ->
                btnSecondary.text = btnModel.label?.asString(requireContext())
                btnSecondary.setOnClickListener {
                    btnModel.onClick?.onClick(it)
                    dismiss()
                }
            } ?: btnSecondary.apply {
                isGone = true
            }
        }
        return MaterialAlertDialogBuilder(
            requireContext(),
            R.style.AlertDialogTheme
        ).setView(binding.root).create()
    }
}
