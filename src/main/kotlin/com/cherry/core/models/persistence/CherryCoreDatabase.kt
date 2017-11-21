package com.cherry.core.models.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.cherry.core.data.store.ConversationDataStore
import com.cherry.core.data.store.MessageDataStore
import com.cherry.core.data.store.ParticipantDataStore
import com.cherry.core.models.Conversation
import com.cherry.core.models.Message
import com.cherry.core.models.Participant

/**
 * Created by girish on 11/12/17.
 */

@TypeConverters(com.cherry.core.utilities.TypeConverters::class)
@Database(entities = arrayOf(Message::class, Participant::class, Conversation::class), version = 1)
abstract class CherryCoreDatabase : RoomDatabase() {
    abstract fun getMessageDataStore(): MessageDataStore
    abstract fun getParticipantDataStore(): ParticipantDataStore
    abstract fun getConversationDataStore(): ConversationDataStore
}