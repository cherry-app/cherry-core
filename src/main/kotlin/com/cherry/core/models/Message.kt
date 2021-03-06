package com.cherry.core.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by girish on 11/11/17.
 */

@Entity(tableName = "Messages",
        indices = arrayOf(Index(value = "recipientId", name = "recipientId"), Index(value = "senderId", name = "senderId")),
        foreignKeys = arrayOf(
                ForeignKey(entity = Participant::class, parentColumns = arrayOf("id"),
                childColumns = arrayOf("recipientId"),
                onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = Participant::class, parentColumns = arrayOf("id"),
                        childColumns = arrayOf("senderId"),
                        onDelete = ForeignKey.CASCADE)))
data class Message(@PrimaryKey val id: Long?, val senderId: String, val recipientId: String, var content: String, var state: MessageState, val sentTime: Long, val receivedTime: Long, val unread: Boolean): Serializable