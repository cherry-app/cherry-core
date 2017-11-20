package com.cherry.core.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

/**
 * Created by girish on 11/20/17.
 */

class Conversation {

    @Embedded
    var participant: Participant? = null

    @Relation(parentColumn = "id", entityColumn = "senderId")
    var messagesFrom: List<Message>? = null

    @Relation(parentColumn = "id", entityColumn = "recipientId")
    var messagesTo: List<Message>? = null

    fun getUnreadCount() = messagesFrom?.filter { it.unread }?.count() ?: 0
}