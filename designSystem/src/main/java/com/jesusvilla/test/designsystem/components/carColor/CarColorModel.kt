package com.jesusvilla.test.designsystem.components.carColor

import androidx.annotation.DrawableRes
import com.jesusvilla.test.designsystem.R

data class CarColorModel(
    val carId: Int,
    @DrawableRes val assetId: Int
) {
    companion object {

        private val idToDrawableResMap = mapOf(
            1 to R.drawable.ic_blue_car_no_background,
            2 to R.drawable.ic_white_car_no_background,
            3 to R.drawable.ic_red_car_no_background,
            4 to R.drawable.ic_black_car_no_background,
            5 to R.drawable.ic_gray_car_no_background,
            6 to R.drawable.ic_green_car_no_background,
        )

        fun assignRandomId(): Int {
            return idToDrawableResMap.keys.random()
        }

        fun getDrawableResForId(id: Int): Int {
            return if (id > idToDrawableResMap.size || id < 1) idToDrawableResMap[1]!! else idToDrawableResMap[id]!!
        }
    }
}
