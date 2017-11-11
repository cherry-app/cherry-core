package com.cherry.core.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.cherry.core.models.persistence.RecipientType

/**
 * Created by girish on 11/11/17.
 */

@Entity(tableName = "Recipients")
data class Recipient(@PrimaryKey val id: String, val contactId: Long, val displayName: String, val blocked: Boolean, val status: String, val type: RecipientType)