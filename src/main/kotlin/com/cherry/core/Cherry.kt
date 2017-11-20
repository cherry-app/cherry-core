package com.cherry.core

import android.arch.lifecycle.LiveData
import android.content.Context
import com.cherry.core.data.repositories.CoreDataRepository
import com.cherry.core.interactors.MessageInteractor
import com.cherry.core.interactors.ParticipantsInteractor
import com.cherry.core.interactors.SessionInteractor
import com.cherry.core.models.Conversation
import com.cherry.core.models.Participant
import java.lang.ref.WeakReference

/**
 * Created by girish on 11/12/17.
 */

object Cherry {

    private const val CHERRY_PREFS = "com.cherry.core.prefs"

    private const val KEY_SESSION_TOKEN = "sessionToken"
    private const val KEY_LOGIN_TOKEN = "loginToken"
    private const val KEY_UID = "phoneNumber"

    private var contextRef: WeakReference<Context>? = null

    object Session {

        private val sessionInteractor =  SessionInteractor()
        var loginToken: String? = null
        var sessionToken: String? = null
        var uid: String? = null

        val isLoggedIn: Boolean
        get() = sessionToken != null

        fun requestOtp(phoneNumber: String, name: String, onOtpRequested: (success: Boolean, exception: Throwable?) -> Unit) {
            sessionInteractor.signUp(phoneNumber, name, { loginToken, throwable ->
                this.loginToken = loginToken
                if (loginToken != null) {
                    contextRef?.get()
                            ?.getSharedPreferences(CHERRY_PREFS, Context.MODE_PRIVATE)
                            ?.edit()?.putString(KEY_LOGIN_TOKEN, loginToken)?.apply()
                    contextRef?.get()
                            ?.getSharedPreferences(CHERRY_PREFS, Context.MODE_PRIVATE)
                            ?.edit()?.putString(KEY_UID, phoneNumber)?.apply()
                }
                onOtpRequested(loginToken != null, throwable)
            })
        }

        fun verifyOtp(otp: String, onLoginCompleted: (success: Boolean, exception: Throwable?) -> Unit) {
            val token = loginToken
            if (token == null) {
                onLoginCompleted(false, null)
                return
            }
            sessionInteractor.verifyOtp(otp, token, { sessionToken, throwable ->
                this.sessionToken = sessionToken
                if (sessionToken != null) {
                    contextRef?.get()
                            ?.getSharedPreferences(CHERRY_PREFS, Context.MODE_PRIVATE)
                            ?.edit()?.putString(KEY_SESSION_TOKEN, sessionToken)?.apply()
                }
                onLoginCompleted(sessionToken != null, throwable)
            })
        }

        fun resendOtp(phoneNumber: String, onOtpResent: (attemptsLeft: Int, exception: Throwable?) -> Unit) {
            val token = loginToken
            if (token == null) {
                onOtpResent(-1, null)
                return
            }
            sessionInteractor.resendOtp(phoneNumber, token, { attemptsLeft, throwable ->
                onOtpResent(attemptsLeft, throwable)
            })
        }
    }

    object Contacts {

        @Volatile var isSyncing = false

        private val mCallbackRefs = ArrayList<WeakReference<() -> Unit>>()
        private val participantsInteractor = ParticipantsInteractor()

        fun sync(context: Context, onSyncComplete: () -> Unit = {}) {
            mCallbackRefs.add(WeakReference(onSyncComplete))
            if (isSyncing) {
                return
            }
            isSyncing = true
            participantsInteractor.sync(context, {
                mCallbackRefs.forEach { it.get()?.invoke() }
                mCallbackRefs.clear()
                participantsInteractor.clear()
                isSyncing = false
            })
        }

        fun getParticipantsLiveData(context: Context): LiveData<List<Participant>> =
            CoreDataRepository.getLocalDataRepository(context).getParticipantDataStore().getParticipantsLiveData()
    }

    object Messaging {

        fun getConversationLiveData(context: Context): LiveData<List<Conversation>> =
            CoreDataRepository.getLocalDataRepository(context).getConversationDataStore().getConversations()

        fun postTextMessage(message: String, recipientId: String, onMessagePosted: (Boolean) -> Unit) {
            val token = Cherry.Session.sessionToken ?: return
            MessageInteractor().postMessage(message, recipientId, token, onMessagePosted)
        }
    }

    lateinit var partnerId: String

    fun init(context: Context, partnerId: String) {
        this.partnerId = partnerId
        contextRef = WeakReference(context.applicationContext)
        val sharedPreferences = context.getSharedPreferences(CHERRY_PREFS, Context.MODE_PRIVATE)
        Session.sessionToken = sharedPreferences.getString(KEY_SESSION_TOKEN, null)
        Session.loginToken = sharedPreferences.getString(KEY_LOGIN_TOKEN, null)
        Session.uid = sharedPreferences.getString(KEY_UID, null)
    }
}