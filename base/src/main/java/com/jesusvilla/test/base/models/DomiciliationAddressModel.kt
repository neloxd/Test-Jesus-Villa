package com.jesusvilla.test.base.models

import android.os.Parcelable
import com.jesusvilla.test.base.constants.BaseConstants
import kotlinx.parcelize.Parcelize

const val NEW_ADDRESS = "Nuevo domicilio..."

@Parcelize
data class DomiciliationAddressModel(
    val id: Long = 0,
    val street: String = "",
    val externalNumber: String = "",
    val internalNumber: String = "",
    val zipCode: String = "",
    val state: String = "",
    val townHall: String = "",
    val city: String = "",
    val colony: String = "",
    var last: Boolean = false
) : Parcelable {

    fun toStringExceptStreet(): String {
        return if (!last) {
            val address = StringBuilder()
            var comma = ""

            if (colony.isNotEmpty()) {
                address.append(colony.trim { it <= ' ' })
                comma = ", "
            }
            if (townHall.isNotEmpty()) {
                address.append(comma)
                address.append(townHall.trim { it <= ' ' })
            }
            if (zipCode.isNotEmpty() || state.isNotEmpty()) {
                address.append(comma)
                address.append("\n")
            }
            comma = ""
            if (zipCode.isNotEmpty()) {
                address.append(zipCode.trim { it <= ' ' })
                comma = ", "
            }
            if (state.isNotEmpty()) {
                address.append(comma)
                address.append(state.trim { it <= ' ' })
            }
            address.toString()
        } else {
            BaseConstants.NEW_ADDRESS
        }
    }

    override fun toString(): String {
        if (last) return BaseConstants.NEW_ADDRESS
        return buildString {
            append("${street.trim()} ${externalNumber.trim()} ${internalNumber.trim()}".trim())
            if (colony.isNotEmpty() || townHall.isNotEmpty()) appendLine()
            append("${colony.trim()}, ${townHall.trim()}".trim())
            if (zipCode.isNotEmpty() || state.isNotEmpty()) appendLine()
            append("${zipCode.trim()}, ${state.trim()}".trim())
        }
    }
}
