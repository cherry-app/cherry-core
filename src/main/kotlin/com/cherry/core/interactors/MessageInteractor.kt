package com.cherry.core.interactors

import com.cherry.core.controllers.MessageController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by girish on 11/12/17.
 */

class MessageInteractor {

    fun postMessage(text: String, recipientId: String, token: String, onMessagePosted: (success: Boolean) -> Unit) {
        Observable.fromCallable { MessageController().postMessage(text, recipientId, token) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isSuccessful && response.code() == 200) {
                        onMessagePosted(true)
                    } else {
                        onMessagePosted(false)
                    }
                }
    }

}