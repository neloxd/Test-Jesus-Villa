package com.jesusvilla.test.core.network

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject

@Suppress("TooManyFunctions")
class PersistentCookieStore @Inject constructor(
    @ApplicationContext context: Context
) : CookieStore {

    private var sharedPreferences: SharedPreferences
    private var allCookies: MutableMap<URI, MutableSet<HttpCookie>> = mutableMapOf()

    companion object {
        private const val SP_COOKIE_STORE = "cookieStore"
        private const val SP_KEY_DELIMITER = "|"
        private const val SP_KEY_DELIMITER_REGEX = "\\" + SP_KEY_DELIMITER
    }

    init {
        sharedPreferences = context.getSharedPreferences(
            SP_COOKIE_STORE,
            Context.MODE_PRIVATE
        )
        loadAllFromPersistence()
    }

    @Suppress("TooGenericExceptionCaught")
    private fun loadAllFromPersistence() {
        val allPairs = sharedPreferences.all
        try {
            allPairs.forEach { (key, value) ->
                val uriAndName: Array<String> =
                    key.split(SP_KEY_DELIMITER_REGEX.toRegex(), limit = 2)
                        .toTypedArray()
                val uri = URI(uriAndName[0])
                val encodedCookie: String = value as String
                val cookie: HttpCookie = Gson().fromJson(encodedCookie, HttpCookie::class.java)
                var targetCookies: MutableSet<HttpCookie>? = allCookies[uri]
                if (targetCookies == null) {
                    targetCookies = mutableSetOf()
                    allCookies[uri] = targetCookies
                }
                targetCookies.add(cookie)
            }
        } catch (e: Exception) {
            Timber.w(e, "OOps")
        }
    }

    private fun cookieUri(uri: URI, cookie: HttpCookie): URI? {
        var cookieUri = uri
        if (cookie.domain != null) {
            var domain = cookie.domain
            if (domain[0] == '.') {
                domain = domain.substring(1)
            }
            try {
                cookieUri = URI(
                    if (uri.scheme == null) "http" else uri.scheme, domain,
                    if (cookie.path == null) "/" else cookie.path, null
                )
            } catch (e: URISyntaxException) {
                Timber.w(e, "OOps")
            }
        }
        return cookieUri
    }

    override fun add(uriParams: URI?, cookie: HttpCookie?) {
        if (uriParams == null || cookie == null) return
        val newUri = cookieUri(uriParams, cookie) ?: return

        var targetCookies: MutableSet<HttpCookie>? = allCookies?.get(newUri)
        if (targetCookies == null) {
            targetCookies = HashSet()
            allCookies?.put(newUri, targetCookies)
        }
        targetCookies.remove(cookie)
        targetCookies.add(cookie)

        saveToPersistence(newUri, cookie)
    }

    private fun saveToPersistence(uri: URI, cookie: HttpCookie) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(
            uri.toString() + SP_KEY_DELIMITER + cookie.name,
            Gson().toJson(uri)
        )
        editor.apply()
    }

    override fun get(uri: URI?): MutableList<HttpCookie> {
        if (uri == null) return mutableListOf()
        return getValidCookies(uri).toMutableList()
    }

    private fun getValidCookies(uri: URI): List<HttpCookie> {
        val targetCookies: MutableSet<HttpCookie> = java.util.HashSet()
        // If the stored URI does not have a path then it must match any URI in
        // the same domain
        val it: Iterator<URI> = allCookies.keys.iterator()
        while (it.hasNext()) {
            val storedUri = it.next()
            // Check ith the domains match according to RFC 6265
            if (checkDomainsMatch(storedUri.host, uri.host) && checkPathsMatch(
                    storedUri.path,
                    uri.path
                )
            ) {
                targetCookies.addAll(allCookies[storedUri]!!)
            }
        }

        // Check it there are expired cookies and remove them
        if (!targetCookies.isEmpty()) {
            val cookiesToRemoveFromPersistence: MutableList<HttpCookie> = ArrayList()
            val it = targetCookies.iterator()
            while (it
                    .hasNext()
            ) {
                val currentCookie = it.next()
                if (currentCookie != null && currentCookie.hasExpired()) {
                    cookiesToRemoveFromPersistence.add(currentCookie)
                    it.remove()
                }
            }
            if (!cookiesToRemoveFromPersistence.isEmpty()) {
                removeFromPersistence(uri, cookiesToRemoveFromPersistence)
            }
        }
        return ArrayList(targetCookies)
    }

    private fun removeFromPersistence(uri: URI, cookiesToRemove: List<HttpCookie>) {
        val editor = sharedPreferences.edit()
        for (cookieToRemove: HttpCookie in cookiesToRemove) {
            editor.remove(
                uri.toString() + SP_KEY_DELIMITER +
                        cookieToRemove.name
            )
        }
        editor.apply()
    }

    private fun checkDomainsMatch(cookieHost: String, requestHost: String): Boolean {
        return requestHost == cookieHost || requestHost.endsWith(".$cookieHost")
    }

    private fun checkPathsMatch(cookiePath: String, requestPath: String): Boolean {
        return requestPath == cookiePath ||
                requestPath.startsWith(cookiePath) &&
                cookiePath[cookiePath.length - 1] == '/' ||
                requestPath.startsWith(
                    cookiePath
                ) && requestPath.substring(cookiePath.length)[0] == '/'
    }

    override fun getCookies(): MutableList<HttpCookie> {
        val allValidCookies: MutableList<HttpCookie> = mutableListOf()
        allCookies.keys.forEach {
            allValidCookies.addAll(getValidCookies(it))
        }
        return allValidCookies
    }

    override fun getURIs(): MutableList<URI> {
        return java.util.ArrayList(allCookies.keys)
    }

    override fun remove(uri: URI?, cookie: HttpCookie?): Boolean {
        val targetCookies: MutableSet<HttpCookie>? = allCookies[uri]
        val cookieRemoved = targetCookies?.remove(cookie) ?: false
        if (cookieRemoved) {
            removeFromPersistence(uri, cookie)
        }
        return cookieRemoved
    }

    private fun removeFromPersistence(uri: URI?, cookieToRemove: HttpCookie?) {
        val editor = sharedPreferences.edit()
        editor.remove(
            uri.toString() + SP_KEY_DELIMITER +
                    cookieToRemove?.name
        )
        editor.apply()
    }

    override fun removeAll(): Boolean {
        allCookies.clear()
        removeAllFromPersistence()
        return true
    }

    private fun removeAllFromPersistence() {
        sharedPreferences.edit().clear().apply()
    }
}
