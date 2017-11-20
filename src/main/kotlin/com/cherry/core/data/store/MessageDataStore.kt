package com.cherry.core.data.store

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cherry.core.models.Message

/**
 * Created by girish on 11/12/17.
 */

@Dao
interface MessageDataStore {

    @Query("SELECT * FROM Messages WHERE (recipientId = :participantId OR senderId = :participantId) AND sentTime <= :since ORDER BY sentTime DESC LIMIT :limit")
    fun getMessagesForConversation(participantId: String, since: Long, limit: Int): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)
}