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

    @Query("SELECT * FROM Messages WHERE (recipientId = :recipientId OR senderId = :recipientId) AND sentTime <= :since LIMIT :limit ORDER BY sentTime DESC")
    fun getMessagesForConversation(recipientId: String, since: Long, limit: Int): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)
}