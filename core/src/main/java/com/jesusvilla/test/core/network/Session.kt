package com.jesusvilla.test.core.network

@Suppress("MemberNameEqualsClassName")
object Session {
    var currentUser: Any? = null
    var session: HashSet<String> = HashSet()
    var authenticationListener: AuthenticationListener? = null
    var isOnline = false

    fun invalidate() {
        // sending logged out event to it's listener
        // i.e: Activity, Fragment, Service
        session.clear()
        authenticationListener?.onUserLoggedOut()
    }
}

fun interface AuthenticationListener {
    fun onUserLoggedOut()
}
