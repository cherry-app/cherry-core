package com.cherry.core.interactors

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by girish on 11/19/17.
 */

abstract class Interactor: LifecycleObserver {

    private val explicitClear: Boolean

    constructor(): this(false)

    constructor(explicitClear: Boolean) {
        this.explicitClear = explicitClear
    }

    protected val disposableList = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!explicitClear) {
            disposableList.clear()
        }
    }

    fun clear() {
        disposableList.clear()
    }
}