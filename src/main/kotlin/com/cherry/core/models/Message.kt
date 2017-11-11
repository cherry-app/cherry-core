package com.cherry.core.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by girish on 11/11/17.
 */

@Entity(tableName = "Messages",
        foreignKeys = arrayOf(
                ForeignKey(entity = Recipient::class, parentColumns = arrayOf("id"),
                childColumns = arrayOf("recipientId"),
                onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = Recipient::class, parentColumns = arrayOf("id"),
                        childColumns = arrayOf("senderId"),
                        onDelete = ForeignKey.CASCADE)))
data class Message(@PrimaryKey val id: Long, val senderId: String, val recipientId: String, val state: MessageState, val sentTime: Long, val receivedTime: Long)