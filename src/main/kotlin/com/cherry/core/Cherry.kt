package com.cherry.core

import android.content.Context
import com.cherry.core.interactors.SessionInteractor
import java.lang.ref.WeakReference

/**
 * Created by girish on 11/12/17.
 */

object Cherry {

    private const val CHERRY_PREFS = "com.cherry.core.prefs"

    private const val KEY_SESSION_TOKEN = "sessionToken"

    private var contextRef: WeakReference<Context>? = null

    object Session {

        private val sessionInteractor =  SessionInteractor()
        private var loginToken: String? = null
        var sessionToken: String? = null

        val isLoggedIn: Boolean
        get() = sessionToken != null

        fun requestOtp(phoneNumber: String, name: String, onOtpRequested: (success: Boolean) -> Unit) {
            sessionInteractor.signUp(phoneNumber, name, { loginToken ->
                this.loginToken = loginToken
                onOtpRequested(loginToken != null)
            })
        }

        fun verifyOtp(otp: String, onLoginCompleted: (success: Boolean) -> Unit) {
            val token = loginToken
            if (token == null) {
                onLoginCompleted(false)
                return
            }
            sessionInteractor.verifyOtp(otp, token, { sessionToken ->
                this.sessionToken = sessionToken
                if (sessionToken != null) {
                    contextRef?.get()
                            ?.getSharedPreferences(CHERRY_PREFS, Context.MODE_PRIVATE)
                            ?.edit()?.putString(KEY_SESSION_TOKEN, sessionToken)?.apply()
                }
                onLoginCompleted(sessionToken != null)
            })
        }

    }

    lateinit var partnerId: String

    fun init(context: Context, partnerId: String) {
        this.partnerId = partnerId
        contextRef = WeakReference(context.applicationContext)
        val sharedPreferences = context.getSharedPreferences(CHERRY_PREFS, Context.MODE_PRIVATE)
        Session.sessionToken = sharedPreferences.getString(KEY_SESSION_TOKEN, null)
    }

}