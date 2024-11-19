package com.jesusvilla.test.base.ui

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jesusvilla.test.base.extension.forceHideKeyboard

abstract class BaseBottomSheetDialog<S : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> S
) :
    BottomSheetDialogFragment() {

    private var _binding: S? = null
    protected val binding get() = _binding!!
    protected var hasTransparent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forceHideKeyboard()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forceHideKeyboard()
        _binding = bindingInflater(inflater, container, false)
        if (hasTransparent) removeTransparentBackground()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
    }

    abstract fun setupUI()

    abstract fun setupListeners()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }
        return bottomSheetDialog
    }

    private fun removeTransparentBackground() {
        dialog?.let { d ->
            d.window?.let { w ->
                {
                    with(w) {
                        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        requestFeature(Window.FEATURE_NO_TITLE)
                    }
                }
            }
        }
    }
}
