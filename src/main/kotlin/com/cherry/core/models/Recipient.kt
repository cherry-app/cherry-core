package com.cherry.core.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by girish on 11/11/17.
 */

@Entity(tableName = "Recipients")
data class Recipient(@PrimaryKey val id: Long, val contactId: Long, val blocked: Boolean, val status: String)