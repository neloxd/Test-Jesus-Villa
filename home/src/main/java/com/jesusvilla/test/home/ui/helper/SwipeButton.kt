package com.jesusvilla.test.home.ui.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.jesusvilla.test.home.ui.listener.SwipeButtonListener

class SwipeButton(
    private val context: Context,
    private val imgResId: Int,
    private val color: Int,
    private val listener: SwipeButtonListener
) {
    private var position: Int = 0
    private var clickRegion: RectF? = null
    private val resources: Resources = context.resources

    fun onClick(x: Float, y: Float): Boolean {
        if (clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(position)
            return true
        }
        return false
    }

    fun onDraw(c: Canvas, rectF: RectF, pos: Int) {
        val p = Paint()
        p.color = resources.getColor(color, null)
        c.drawRect(rectF, p)

        if (imgResId != 0) {
            val d = ContextCompat.getDrawable(context, imgResId)
            val bitmap = drawableToBitmap(d)
            val iconWidth = bitmap.width
            val iconHeight = bitmap.height
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val left = centerX - iconWidth / 2
            val top = centerY - iconHeight / 2
            c.drawBitmap(bitmap, left, top, p)
        }

        clickRegion = rectF
        this.position = pos
    }
    private fun drawableToBitmap(d: Drawable?): Bitmap {
        if (d is BitmapDrawable) return d.bitmap
        val bitmap =
            Bitmap.createBitmap(d!!.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        d.setBounds(0, 0, canvas.width, canvas.height)
        d.draw(canvas)
        return bitmap
    }
}
