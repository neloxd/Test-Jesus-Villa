@file:Suppress("SwallowedException")

package com.jesusvilla.test.base.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import com.jesusvilla.test.base.BuildConfig

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Activity.makeToastShort(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.makeToastLong(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Activity.getColorFromAttr(attrId: Int): Int {
    val typedValue = TypedValue()
    val theme = theme
    theme.resolveAttribute(attrId, typedValue, true)
    return ContextCompat.getColor(this, typedValue.resourceId)
}

fun Activity.launchGoogleReviewIntent() {
    val appMarketUri = "http://play.google.com/store/apps/details?id=com.idmexico.tutag".toUri()
    val goToMarketIntent = Intent(Intent.ACTION_VIEW, appMarketUri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    }

    try {
        startActivity(goToMarketIntent)
    } catch (e: ActivityNotFoundException) {
        val appBrowserUri =
            "http://play.google.com/store/apps/details?id=com.idmexico.tutag".toUri()
        startActivity(Intent(Intent.ACTION_VIEW, appBrowserUri))
    }
}

fun Activity.launchGoogleReviewManager(
    onReviewSuccess: () -> Unit,
    onReviewFailure: () -> Unit = {}
) {

    val reviewManager = if (BuildConfig.DEBUG) {
        FakeReviewManager(applicationContext)
    } else {
        ReviewManagerFactory.create(applicationContext)
    }

    val requestReviewTask = reviewManager.requestReviewFlow()
    requestReviewTask.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            val flow = reviewManager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener { taskFlow ->
                if (taskFlow.isSuccessful) {
                    onReviewSuccess()
                } else {
                    onReviewFailure()
                }
            }
        }
    }
}
