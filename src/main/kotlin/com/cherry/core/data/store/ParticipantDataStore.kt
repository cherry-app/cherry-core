package com.cherry.core.data.store

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cherry.core.models.Participant

/**
 * Created by girish on 11/12/17.
 */

@Dao
interface ParticipantDataStore {

    @Query("SELECT * FROM Participants")
    fun getParticipants(): List<Participant>

    @Query("SELECT id FROM Participants")
    fun getParticipantUids(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParticipant(participant: Participant)

    @Query("SELECT * FROM Participants")
    fun getParticipantsLiveData(): LiveData<List<Participant>>

    @Query("SELECT * FROM Participants where id = :participantId")
    fun getParticipantById(participantId: String): Participant?

    @Query("SELECT * FROM Participants where id in (:participantIds)")
    fun getParticipantById(participantIds: List<String>): List<Participant>

}