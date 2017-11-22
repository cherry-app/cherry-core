package com.cherry.core.controllers

import android.content.Context
import com.cherry.core.Cherry
import com.cherry.core.data.repositories.CoreDataRepository
import com.cherry.core.models.Conversation
import com.cherry.core.models.Message
import com.cherry.core.models.MessageState

/**
 * Created by girish on 11/12/17.
 */

class MessageController {

    fun queueMessage(context: Context, text: String, recipientId: String) {
        val time = System.currentTimeMillis()
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val message = Message(null, uid, recipientId, text, MessageState.PENDING, time, -1, false)
        CoreDataRepository.getLocalDataRepository(context).getMessageDataStore().insertMessage(message)
        val conversation = Conversation(null, recipientId, time, text)
        CoreDataRepository.getLocalDataRepository(context).getConversationDataStore().insertOrReplaceConversation(conversation)
    }
}