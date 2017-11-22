package com.cherry.core.interactors

import android.content.Context
import com.cherry.core.controllers.MessageController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by girish on 11/12/17.
 */

class MessageInteractor: Interactor() {

    fun queueMessage(context: Context, text: String, recipientId: String, onMessageQueued: (Unit) -> Unit) {
        disposableList.add(Observable.fromCallable { MessageController().queueMessage(context, text, recipientId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onMessageQueued, {}))
    }

}