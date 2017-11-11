package com.cherry.core.data.store

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cherry.core.models.Recipient

/**
 * Created by girish on 11/12/17.
 */

@Dao
interface RecipientDataStore {

    @Query("SELECT * FROM Recipients")
    fun getRecipients()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipient(recipient: Recipient)
}