package com.jesusvilla.test.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

// Set the radius of the Blur. Supported range 0 < radius <= 25
private const val BLUR_RADIUS = 25f

fun Context.blur(image: Bitmap): Bitmap {
    val outputBitmap = Bitmap.createBitmap(image)
    val renderScript = RenderScript.create(this)
    val tmpIn = Allocation.createFromBitmap(renderScript, image)
    val tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap)

    // Intrinsic Gausian blur filter
    val theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    theIntrinsic.setRadius(BLUR_RADIUS)
    theIntrinsic.setInput(tmpIn)
    theIntrinsic.forEach(tmpOut)
    tmpOut.copyTo(outputBitmap)
    return outputBitmap
}

fun Context.getDrawableFromResources(@DrawableRes resId: Int): Drawable {
    return AppCompatResources.getDrawable(this, resId)!!
}
