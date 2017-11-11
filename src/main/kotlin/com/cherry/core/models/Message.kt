package com.cherry.core.models

/**
 * Created by girish on 11/11/17.
 */

data class Message(val id: Long, val senderId: Long, val recipientId: Long, val state: MessageState)