package com.jesusvilla.test.designsystem.components.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jesusvilla.test.designsystem.R

class CustomPaseHeader(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val ivLogoPase: ImageView
    private val tvTitle: TextView
    private val tvDescription: TextView

    init {
        val binding = LayoutInflater.from(context).inflate(R.layout.custom_pase_header, this, true)
        ivLogoPase = binding.findViewById(R.id.iv_logo_pase)
        tvTitle = binding.findViewById(R.id.tv_title)
        tvDescription = binding.findViewById(R.id.tv_subtitle)
    }

    fun setup(title: String? = null, description: String? = null, showImage: Boolean = true) {
        ivLogoPase.isVisible = showImage
        title?.let {
            tvTitle.text = it
            tvTitle.isVisible = true
        }
        description?.let {
            tvDescription.text = description
            tvDescription.isVisible = true
        }
    }
}
