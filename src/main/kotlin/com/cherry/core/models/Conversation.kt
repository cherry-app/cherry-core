package com.cherry.core.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by girish on 11/20/17.
 */

@Entity(tableName = "Conversations",
        indices = arrayOf(Index(value = "participantId", name = "participant", unique = true)),
        foreignKeys = arrayOf(
                ForeignKey(entity = Participant::class, parentColumns = arrayOf("id"),
                        childColumns = arrayOf("participantId"),
                        onDelete = ForeignKey.CASCADE)))
data class Conversation(@PrimaryKey val id: Long?, val participantId: String, val lastReceivedTimestamp: Long, val snippet: String): Serializable