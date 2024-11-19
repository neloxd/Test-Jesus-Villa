package com.jesusvilla.test.designsystem.components.toast

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.jesusvilla.test.designsystem.R

object MyToast {
    @SuppressLint("ResourceAsColor")
    fun show(params: ToastParams) {

        val inflater = LayoutInflater.from(params.context)
        val layout: View = inflater.inflate(R.layout.toast_layout, null)
        val image = layout.findViewById<View>(R.id.toast_image) as ImageView
        val textV = layout.findViewById<View>(R.id.toast_text) as TextView
        textV.text = params.text

        image.isVisible = params.showImage

        params.textColor?.let {
            textV.setTextColor(params.textColor)
        }

        val toast = Toast(params.context)
        params.background?.let {
            layout.background.setColorFilter(
                params.context!!.getColor(params.background),
                PorterDuff.Mode.SRC_IN
            )
        }
        toast.duration = params.duration
        toast.setView(layout)
        toast.show()
    }
}
data class ToastParams(
    val context: Context?,
    val text: String?,
    val duration: Int,
    val showImage: Boolean = true,
    val background: Int? = null,
    val textColor: Int? = null
)
