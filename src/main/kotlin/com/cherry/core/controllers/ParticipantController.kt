package com.cherry.core.controllers

import android.content.Context
import com.cherry.core.data.repositories.CoreDataRepository
import com.cherry.core.models.Participant

/**
 * Created by girish on 11/28/17.
 */

class ParticipantController {

    fun findParticipantById(context: Context, participantId: String): Participant? =
        CoreDataRepository.getLocalDataRepository(context).getParticipantDataStore().getParticipantById(participantId)

}