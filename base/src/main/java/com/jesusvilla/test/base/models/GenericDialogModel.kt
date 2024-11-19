package com.jesusvilla.test.base.models

import android.view.View
import com.jesusvilla.test.base.utils.UiText

class GenericDialogModel private constructor(
    val title: UiText? = null,
    val body: UiText? = null,
    val dialogOptions: DialogOptions = DialogOptions()
) {
    data class DialogOptions(
        val primaryButton: DialogButtonModel? = null,
        val secondaryButton: DialogButtonModel? = null,
        val isCancellable: Boolean = true,
    )

    @Suppress("TooManyFunctions")
    class Builder {
        private var title: UiText? = null
        private var body: UiText? = null
        private var dialogOptions: DialogOptions = DialogOptions()

        fun title(value: UiText?) = apply { this.title = value }
        fun body(value: UiText?) = apply { this.body = value }

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
            GenericDialogModel(
                title, body, dialogOptions
            )
    }
}

data class DialogButtonModel(
    val label: UiText?,
    var onClick: View.OnClickListener? = null
)
