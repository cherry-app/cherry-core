package com.cherry.core.interactors

import com.cherry.core.controllers.SessionController
import com.cherry.core.utilities.getJSONObjectOrNull
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by girish on 11/19/17.
 */

class SessionInteractor: Interactor() {

    fun signUp(phoneNumber: String, name: String, onSignUpCompleted: (loginToken: String?, error: Throwable?) -> Unit) {
        disposableList.add(Observable.fromCallable { SessionController().signUp(phoneNumber, name) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful && response.code() == 200) {
                        val result = getJSONObjectOrNull(response.body())
                        if (result != null) {
                            val token = result.optString("token")
                            onSignUpCompleted(token, null)
                            return@subscribe
                        }
                    }
                    onSignUpCompleted(null, null)
                }, { t -> onSignUpCompleted(null, t) }))
    }

    fun verifyOtp(otp: String, loginToken: String, onLoginCompleted: (authToken: String?, error: Throwable?) -> Unit ) {
        disposableList.add(Observable.fromCallable { SessionController().verifyOtp(otp, loginToken) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { response ->
                    if (response.isSuccessful && response.code() == 200) {
                        val result = getJSONObjectOrNull(response.body())
                        if (result != null) {
                            val token = result.optString("token")
                            onLoginCompleted(token, null)
                            return@subscribe
                        }
                    }
                    onLoginCompleted(null, null)
                }, { t -> onLoginCompleted(null, t) }))
    }

    fun resendOtp(phoneNumber: String, loginToken: String, onOtpResent: (attemptsLeft: Int, error: Throwable?) -> Unit ) {
        disposableList.add(Observable.fromCallable { SessionController().resendOtp(phoneNumber, loginToken) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { response ->
                    if (response.isSuccessful && response.code() == 200) {
                        val result = getJSONObjectOrNull(response.body())
                        if (result != null) {
                            val attemptsLeft = result.optInt("attempts_left")
                            onOtpResent(attemptsLeft, null)
                            return@subscribe
                        }
                    }
                    onOtpResent(-1, null)
                }, { t -> onOtpResent(-1, t) }))
    }
}