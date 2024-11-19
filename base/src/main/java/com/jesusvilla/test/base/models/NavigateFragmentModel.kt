package com.jesusvilla.test.base.models

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions

class NavigateFragmentModel(
    val id: Int,
    var bundle: Bundle?,
    val code: Int = 0,
    var flags: Int = 0,
    val navOptions: NavOptions? = null
) {
    constructor(id: Int, code: Int = 0) : this(id, null, code)

    constructor(id: Int, code: Int = 0, bundle: Bundle.() -> Unit) : this(
        id,
        bundleOf().apply(bundle),
        code
    )
}
