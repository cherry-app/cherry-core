package com.cherry.core.controllers

import android.content.Context
import com.cherry.core.Cherry
import com.cherry.core.data.repositories.CoreDataRepository
import com.cherry.core.models.Conversation
import com.cherry.core.models.Message
import com.cherry.core.models.MessageState
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Response

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

    fun publishUnsentMessages(context: Context): Response<String>? {
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val authToken = Cherry.Session.sessionToken ?: throw IllegalStateException("Auth token not present")

        val messages = CoreDataRepository.getLocalDataRepository(context).getMessageDataStore().getUnsentMessages(uid)

        if (messages.isEmpty()) {
            return null
        }

        val payload = JsonArray()
        messages.forEach { message ->
            val jsonObject = JsonObject()
            jsonObject.addProperty("recipientId", message.recipientId)
            jsonObject.addProperty("content", message.content)
            jsonObject.addProperty("sentTime", message.sentTime)
            payload.add(jsonObject)
        }
        val body = JsonObject()
        body.add("messages", payload)
        return CoreDataRepository.getNetworkDataRepository().postMessage(Cherry.partnerId, uid, authToken, body).execute()
    }

    fun markAsRead(recipientId: String): Response<String> {
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val authToken = Cherry.Session.sessionToken ?: throw IllegalStateException("Auth token not present")

        val body = JsonObject()
        body.addProperty("recipientId", recipientId)
        body.addProperty("timestamp", System.currentTimeMillis())
        return CoreDataRepository.getNetworkDataRepository().markAsRead(Cherry.partnerId, uid, authToken, body).execute()
    }
}