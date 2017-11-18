package com.cherry.core.models.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.cherry.core.data.store.MessageDataStore
import com.cherry.core.data.store.RecipientDataStore
import com.cherry.core.models.Message
import com.cherry.core.models.Recipient

/**
 * Created by girish on 11/12/17.
 */

@TypeConverters(com.cherry.core.utilities.TypeConverters::class)
@Database(entities = arrayOf(Message::class, Recipient::class), version = 1)
abstract class CherryCoreDatabase : RoomDatabase() {
    abstract fun getMessageDataStore(): MessageDataStore
    abstract fun getRecipientDataStore(): RecipientDataStore
}