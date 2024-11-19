package com.jesusvilla.test.base.ui.models

class CustomModalModel(
    val title: CharSequence? = null,
    val body: CharSequence? = null,
    val modalOptions: ModalOptions = ModalOptions(),
    val iconModal: Int? = null
) {
    data class ModalOptions(
        val primaryButton: ModalButtonModel? = null,
        val secondaryButton: ModalButtonModel? = null,
    )

    class Builder {
        private var title: CharSequence? = null
        private var body: CharSequence? = null
        private var modalOptions: ModalOptions = ModalOptions()
        private var icon: Int? = null

        fun title(value: CharSequence?) = apply { this.title = value }
        fun body(value: CharSequence?) = apply { this.body = value }

        fun primaryButton(value: ModalButtonModel?) =
            apply { this.modalOptions = modalOptions.copy(primaryButton = value) }

        fun secondaryButton(value: ModalButtonModel?) =
            apply { this.modalOptions = modalOptions.copy(secondaryButton = value) }

        fun primaryButton(
            label: CharSequence,
            textColor: Int?,
            backGroundColor: Int?,
            onClick: (() -> Unit)?,
            isVisible: Boolean?
        ) =
            apply {
                primaryButton(
                    ModalButtonModel(
                        label,
                        textColor,
                        backGroundColor,
                        onClick,
                        isVisible
                    )
                )
            }

        fun secondaryButton(
            label: CharSequence?,
            textColor: Int?,
            backGroundColor: Int?,
            onClick: (() -> Unit)?,
            isVisible: Boolean?
        ) =
            apply {
                secondaryButton(
                    ModalButtonModel(
                        label,
                        textColor,
                        backGroundColor,
                        onClick,
                        isVisible
                    )
                )
            }

        fun icon(value: Int?) = apply { this.icon = value }
        fun build() =
            CustomModalModel(
                title,
                body,
                modalOptions,
                icon
            )
    }
}

data class ModalButtonModel(
    val label: CharSequence? = null,
    val textColor: Int? = null,
    val backGroundColor: Int? = null,
    var onClick: (() -> Unit)? = null,
    val isVisible: Boolean? = false
)
