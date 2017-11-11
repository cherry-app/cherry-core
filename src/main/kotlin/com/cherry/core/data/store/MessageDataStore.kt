package com.cherry.core.data.store

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

/**
 * Created by girish on 11/12/17.
 */

@Dao
interface MessageDataStore {

    @Query("SELECT * FROM Messages WHERE (recipientId = :recipientId OR senderId =:recipientId) AND sentTime <= :since LIMIT :limit")
    fun getMessagesForConversation(recipientId: Long, since: Long, limit: Int)
}