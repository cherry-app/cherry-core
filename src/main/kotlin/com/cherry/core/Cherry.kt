package com.cherry.core

import com.cherry.core.interactors.SessionInteractor

/**
 * Created by girish on 11/12/17.
 */

object Cherry {

    object Session {

        private val sessionInteractor =  SessionInteractor()
        private var loginToken: String? = null
        private var sessionToken: String? = null

        val isLoggedIn: Boolean
        get() = sessionToken == null

        fun requestOtp(phoneNumber: String, name: String, onOtpRequested: (success: Boolean) -> Unit) {
            sessionInteractor.signUp(phoneNumber, name, { loginToken ->
                this.loginToken = loginToken
                onOtpRequested(loginToken != null)
            })
        }

        fun verifyOtp(otp: String, loginToken: String, onLoginCompleted: (success: Boolean) -> Unit) {
            sessionInteractor.verifyOtp(otp, loginToken, { sessionToken ->
                this.sessionToken = sessionToken
                onLoginCompleted(sessionToken != null)
            })
        }

    }

    lateinit var partnerId: String

    fun init(partnerId: String) {
        this.partnerId = partnerId
    }

}