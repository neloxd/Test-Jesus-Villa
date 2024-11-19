package com.jesusvilla.test.designsystem.components.toast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.jesusvilla.test.designsystem.R

object MyNotificationToast {
    fun show(context: Context?, text: String?, duration: Int) {
        val inflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(R.layout.notification_custom_toast, null)
        val textV = layout.findViewById<View>(R.id.tv_text_snackbar) as TextView
        textV.text = text
        val toast = Toast(context)
        toast.duration = duration
        toast.setView(layout)
        toast.show()
    }
}
