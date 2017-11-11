package com.cherry.core.models.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.cherry.core.models.Message
import com.cherry.core.models.Recipient

/**
 * Created by girish on 11/12/17.
 */

@Database(entities = arrayOf(Message::class, Recipient::class), version = 1)
abstract class CherryCoreDatabase : RoomDatabase()