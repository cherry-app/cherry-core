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

    fun signUp(phoneNumber: String, name: String, onSignUpCompleted: (loginToken: String?) -> Unit) {
        disposableList.add(Observable.fromCallable { SessionController().signUp(phoneNumber, name) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful && response.code() == 200) {
                        val result = getJSONObjectOrNull(response.body())
                        if (result != null) {
                            val token = result.optString("token")
                            onSignUpCompleted(token)
                            return@subscribe
                        }
                    }
                    onSignUpCompleted(null)
                })
    }

    fun verifyOtp(otp: String, loginToken: String, onLoginCompleted: (authToken: String?) -> Unit ) {
        Observable.fromCallable { SessionController().verifyOtp(otp, loginToken) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful && response.code() == 200) {
                        val result = getJSONObjectOrNull(response.body())
                        if (result != null) {
                            val token = result.optString("token")
                            onLoginCompleted(token)
                            return@subscribe
                        }
                    }
                    onLoginCompleted(null)
                }
    }
}