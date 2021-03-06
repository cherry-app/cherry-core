package com.cherry.core.interactors

import android.content.Context
import com.cherry.core.controllers.ParticipantController
import com.cherry.core.controllers.SyncController
import com.cherry.core.models.Participant
import com.cherry.core.utilities.NullableReference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by girish on 11/21/17.
 */

class ParticipantsInteractor: Interactor() {

    fun sync(context: Context, onSyncComplete: () -> Unit) {
        disposableList.add(Observable.fromCallable { SyncController().syncContacts(context) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({onSyncComplete()}, {onSyncComplete()}))
    }

    fun addSelf(context: Context, name: String, uid: String) {
        disposableList.add(Observable.fromCallable { SyncController().insertSelfRecord(context, name, uid) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun findParticipantById(context: Context, participantId: String, onParticipantFound: (Participant?) -> Unit) {
        disposableList.add(Observable.fromCallable { NullableReference(ParticipantController().findParticipantById(context, participantId)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ref -> onParticipantFound(ref.get()) }))
    }
}