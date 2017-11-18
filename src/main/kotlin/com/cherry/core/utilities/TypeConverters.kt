package com.cherry.core.utilities

import android.arch.persistence.room.TypeConverter
import com.cherry.core.models.MessageState
import com.cherry.core.models.persistence.RecipientType

/**
 * Created by girish on 11/19/17.
 */

class TypeConverters {

    @TypeConverter fun toMessageState(state: Int): MessageState = MessageState.fromInt(state)

    @TypeConverter fun from(state: MessageState): Int = state.asInt()

    @TypeConverter fun toRecipientType(type: Int): RecipientType = RecipientType.fromInt(type)

    @TypeConverter fun from(type: RecipientType): Int = type.asInt()

}