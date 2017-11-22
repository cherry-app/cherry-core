package com.cherry.core.data.store

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cherry.core.models.Conversation
import com.cherry.core.models.ConversationWithParticipant

/**
 * Created by girish on 11/20/17.
 */

@Dao
interface ConversationDataStore {

    @Query("SELECT * From Conversations ORDER BY lastReceivedTimestamp DESC")
    fun getConversations(): LiveData<List<ConversationWithParticipant>>

    @Query("SELECT COUNT(1) From Conversations, Messages WHERE recipientId = participantId AND unread = 1")
    fun getConversationUnreadCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceConversation(conversation: Conversation)
}