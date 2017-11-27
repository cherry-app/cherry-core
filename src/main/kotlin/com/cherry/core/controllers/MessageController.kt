package com.cherry.core.controllers

import android.content.Context
import com.cherry.core.Cherry
import com.cherry.core.data.repositories.CoreDataRepository
import com.cherry.core.models.Conversation
import com.cherry.core.models.Message
import com.cherry.core.models.MessageState
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by girish on 11/12/17.
 */

class MessageController {

    fun queueMessage(context: Context, text: String, recipientId: String) {
        val time = System.currentTimeMillis()
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val message = Message(null, uid, recipientId, text, MessageState.PENDING, time, time, false) // time is added to make sure self-sent message comes below in descending order
        CoreDataRepository.getLocalDataRepository(context).getMessageDataStore().insertMessage(message)
        val conversation = Conversation(null, recipientId, time, text)
        CoreDataRepository.getLocalDataRepository(context).getConversationDataStore().insertOrReplaceConversation(conversation)
    }

    fun publishUnsentMessages(context: Context): Pair<Int, Int> {
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val authToken = Cherry.Session.sessionToken ?: throw IllegalStateException("Auth token not present")

        val messages = CoreDataRepository.getLocalDataRepository(context).getMessageDataStore().getUnsentMessages(uid)

        if (messages.isEmpty()) {
            return 0 to 0
        }

        val payload = JsonArray()
        messages.forEach { message ->
            val jsonObject = JsonObject()
            jsonObject.addProperty("id", message.id)
            jsonObject.addProperty("recipientId", message.recipientId)
            jsonObject.addProperty("content", message.content)
            jsonObject.addProperty("sentTime", message.sentTime)
            payload.add(jsonObject)
        }
        val body = JsonObject()
        body.add("messages", payload)

        val response = CoreDataRepository.getNetworkDataRepository().postMessage(Cherry.partnerId, uid, authToken, body).execute()
        if (!response.isSuccessful || response.code() != 200) {
            val error = response.errorBody()?.string()
            return 0 to 0
        }
        val jsonResponse = JSONObject(response.body())
        val successCount = jsonResponse.optJSONArray("succeeded")?.length() ?: 0
        val failureCount = jsonResponse.optJSONArray("failed")?.length() ?: 0

        val succeededIds = jsonResponse.optJSONArray("succeeded")
        val succeededMessages = ArrayList<Long>()
        (0 until succeededIds.length()).mapNotNullTo(succeededMessages) { succeededIds.getString(it).toLongOrNull() }

        CoreDataRepository.getLocalDataRepository(context).getMessageDataStore().markAsSent(succeededMessages)

        return successCount to (successCount + failureCount)
    }

    fun markAsRead(recipientId: String): Response<String> {
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val authToken = Cherry.Session.sessionToken ?: throw IllegalStateException("Auth token not present")

        val body = JsonObject()
        body.addProperty("recipientId", recipientId)
        body.addProperty("timestamp", System.currentTimeMillis())
        return CoreDataRepository.getNetworkDataRepository().markAsRead(Cherry.partnerId, uid, authToken, body).execute()
    }

    fun newIncomingMessage(context: Context, data: Map<String, String>) {
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")

        val senderId = data["senderId"] ?: return
        val content = data["content"] ?: return
        val timestamp = data["sentTime"]?.toLongOrNull() ?: return

        val message = Message(null, senderId, uid, content, MessageState.RECEIVED, timestamp, System.currentTimeMillis(), true)
        CoreDataRepository.getLocalDataRepository(context).getMessageDataStore().insertMessage(message)
    }
}