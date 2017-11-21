package com.cherry.core.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by girish on 11/11/17.
 */

@Entity(tableName = "Participants")
data class Participant(@PrimaryKey val id: String, val contactId: Long, val displayName: String, val blocked: Boolean, val status: String, val type: RecipientType): Serializable