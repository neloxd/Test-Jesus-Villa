package com.jesusvilla.test.base.ui

interface BaseDeepLink {
    fun handleAction(channel: String, action: Int, message: String? = null)

    companion object{
        const val CHANNEL_PAYPAL = "PAYPAL"
        const val CHANNEL_MERCADOAPGO = "MERCADOPAGO"
        const val MESSAGE = "mensaje"
        const val AUTHORIZATION = "autorizacion"
    }
}