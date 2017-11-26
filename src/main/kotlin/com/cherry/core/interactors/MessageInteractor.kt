package com.cherry.core.interactors

import android.content.Context
import com.cherry.core.controllers.MessageController
import com.cherry.core.utilities.NullableReference
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

    fun publishUnsentMessages(context: Context, onMessagesSent: (Boolean) -> Unit) {
        disposableList.add(Observable.fromCallable { NullableReference(MessageController().publishUnsentMessages(context)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseRef ->
                    val response = responseRef.get()
                    if (response == null) {
                        onMessagesSent(false)
                        return@subscribe
                    }
                    onMessagesSent(response.isSuccessful && response.code() == 200)
                }, { onMessagesSent(false) }))

    }

    fun markAsRead(recipientId: String, onMarkedAsRead: () -> Unit) {
        disposableList.add(Observable.fromCallable { MessageController().markAsRead(recipientId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> onMarkedAsRead() }, {}))
    }
}