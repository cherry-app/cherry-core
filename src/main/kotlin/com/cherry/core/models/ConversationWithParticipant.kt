package com.cherry.core.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

/**
 * Created by girish on 11/22/17.
 */

class ConversationWithParticipant {

    @Embedded
    var conversation: Conversation? = null

    @Relation(entity = Participant::class, parentColumn = "participantId", entityColumn = "id")
    var participants: List<Participant>? = null
}