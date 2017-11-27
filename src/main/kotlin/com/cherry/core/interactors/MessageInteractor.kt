package com.cherry.core.interactors

import android.content.Context
import com.cherry.core.controllers.MessageController
import com.cherry.core.models.Message
import com.cherry.core.models.ParticipantWithMessages
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

    fun publishUnsentMessages(context: Context, onMessagesSent: (Int, Int) -> Unit) {
        disposableList.add(Observable.fromCallable { MessageController().publishUnsentMessages(context) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pair ->
                    onMessagesSent(pair.first, pair.second)
                }, { onMessagesSent(0, 0) }))

    }

    fun findUnreadMessages(context: Context, onUnreadMessagesFetched: (List<ParticipantWithMessages>?) -> Unit) {
        disposableList.add(Observable.fromCallable { MessageController().findUnreadMessages(context) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    onUnreadMessagesFetched(result)
                }, { onUnreadMessagesFetched(null) }))
    }

    fun markAsRead(recipientId: String, onMarkedAsRead: () -> Unit) {
        disposableList.add(Observable.fromCallable { MessageController().markAsRead(recipientId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> onMarkedAsRead() }, {}))
    }

    fun markAsReadLocally(context: Context, recipientId: String, onMarkedAsRead: () -> Unit) {
        disposableList.add(Observable.fromCallable { MessageController().markAsReadLocally(context, recipientId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> onMarkedAsRead() }, {}))
    }

    fun newIncomingMessage(context: Context, data: Map<String, String>, onMessageAdded: (Message?) -> Unit) {
        disposableList.add(Observable.fromCallable { NullableReference(MessageController().newIncomingMessage(context, data)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ messageRef -> onMessageAdded(messageRef.get()) }, { onMessageAdded(null) }))
    }
}