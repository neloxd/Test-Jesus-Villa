package com.jesusvilla.test.base.models

import android.view.View
import com.jesusvilla.test.base.utils.UiText

class NotificationsPermissonDialogModel private constructor(
    val dialogOptions: DialogOptions = DialogOptions()
) {
    data class DialogOptions(
        val primaryButton: DialogButtonModel? = null,
        val secondaryButton: DialogButtonModel? = null,
        val isCancellable: Boolean = true,
    )

    class Builder {
        private var dialogOptions: DialogOptions = DialogOptions()

        private fun primaryButton(value: DialogButtonModel?) =
            apply { this.dialogOptions = dialogOptions.copy(primaryButton = value) }

        fun primaryButton(
            label: UiText?,
            onClick: View.OnClickListener? = null,
        ) =
            apply { primaryButton(DialogButtonModel(label, onClick)) }

        private fun secondaryButton(value: DialogButtonModel?) =
            apply { this.dialogOptions = dialogOptions.copy(secondaryButton = value) }

        fun secondaryButton(
            label: UiText?,
            onClick: View.OnClickListener? = null,
        ) = apply { secondaryButton(DialogButtonModel(label, onClick)) }

        fun isCancellable(value: Boolean) =
            apply { this.dialogOptions = dialogOptions.copy(isCancellable = value) }

        fun build() =
            NotificationsPermissonDialogModel(
                dialogOptions
            )
    }
}
