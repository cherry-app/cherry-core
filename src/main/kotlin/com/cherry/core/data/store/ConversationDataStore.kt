package com.cherry.core.data.store

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.cherry.core.models.Conversation

/**
 * Created by girish on 11/20/17.
 */

@Dao
interface ConversationDataStore {

    @Query("SELECT * From Participants")
    fun getConversations(): LiveData<List<Conversation>>

}