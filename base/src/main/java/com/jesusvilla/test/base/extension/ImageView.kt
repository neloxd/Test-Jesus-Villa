package com.jesusvilla.test.base.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.setDrawerImage(drawableResId: Int) {
    Glide.with(context)
        .load(drawableResId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
