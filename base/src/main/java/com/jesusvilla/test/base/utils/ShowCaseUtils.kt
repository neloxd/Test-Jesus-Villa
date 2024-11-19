@file:Suppress("LongParameterList", "ReturnCount")

package com.jesusvilla.test.base.utils

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import com.jesusvilla.test.designsystem.R
import uk.co.deanwild.materialshowcaseview.IShowcaseListener
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

object ShowCaseUtils {

    const val SHOWCASE_HOME_ID_RECHARGE_BUTTON = "sc_recharge_button"
    const val SHOWCASE_HOME_ID_DETAILS_VIEW = "sc_details_view"
    const val SHOWCASE_HOME_ID_MORE_BUTTON = "sc_more_button"
    const val SHOWCASE_HOME_ID_MAIN_MENU_BUTTON = "sc_main_menu_button"
    const val SHOWCASE_HOME_ID_YOUR_TAGS = "sc_your_tags"
    const val SHOWCASE_HOME_ID_PARKING_TICKETS = "sc_parking_tickets"
    const val SHOWCASE_TRANSACTION_ID_DETAIL = "sc_transaction_id_detail"

    private const val SHOWCASE_DELAY = 100

    fun buildShowCaseAndShow(
        activity: Activity,
        target: View?,
        textResource: Int,
        showCaseId: String,
        isHaveMoreTags: Boolean = false,
        nextAction: () -> Unit = {},
        needRectangleWrap: Boolean = false
    ) {
        if (target == null) {
            nextAction()
            return
        }

        if (target.visibility != View.VISIBLE) {
            nextAction()
            return
        }

        if (showCaseId == SHOWCASE_HOME_ID_MORE_BUTTON && !isHaveMoreTags) {
            nextAction()
            return
        }

        if (hasFired(activity, showCaseId)) {
            nextAction()
            return
        }

        val builder = MaterialShowcaseView.Builder(activity)
            .setTarget(target)
            .setDelay(SHOWCASE_DELAY)
            .setMaskColour(ContextCompat.getColor(activity, R.color.showcase_mask))
            .setDismissOnTouch(true)
            .setDismissText(activity.getString(R.string.home_showcase_ok_button))
            .setDismissTextColor(ContextCompat.getColor(activity, R.color.showcase_text_color))
            .setContentTextColor(ContextCompat.getColor(activity, R.color.showcase_text_color))
            .setContentText(activity.getString(textResource))
            .singleUse(showCaseId)

        if (needRectangleWrap) {
            builder.withRectangleShape()
        }

        val materialShowCaseView = builder.build()

        materialShowCaseView.addShowcaseListener(
            object : IShowcaseListener {
                override fun onShowcaseDisplayed(showcaseView: MaterialShowcaseView?) = Unit

                override fun onShowcaseDismissed(showcaseView: MaterialShowcaseView?) {
                    nextAction()
                }
            }
        )

        materialShowCaseView.show(activity)
    }

    private fun hasFired(activity: Activity, showCaseId: String): Boolean {
        return activity.getSharedPreferences("material_showcaseview_prefs", 0)
            .getInt("status_$showCaseId", 0) == -1
    }
}
