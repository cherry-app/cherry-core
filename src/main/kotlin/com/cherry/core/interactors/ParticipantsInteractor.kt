package com.cherry.core.interactors

import android.content.Context
import com.cherry.core.controllers.SyncController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by girish on 11/21/17.
 */

class ParticipantsInteractor: Interactor() {

    fun sync(context: Context, onSyncComplete: () -> Unit) {
        Observable.fromCallable { SyncController().syncContacts(context) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({onSyncComplete()}, {onSyncComplete()})
    }

}