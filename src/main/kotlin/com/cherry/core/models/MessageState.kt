package com.cherry.core.models

/**
 * Created by girish on 11/11/17.
 */

enum class MessageState {
    SENT, DELIVERED, READ, PENDING, RECEIVED, UNKNOWN;

    fun asInt(): Int =
        when(this) {
            SENT -> 1
            DELIVERED -> 2
            READ -> 3
            PENDING -> 4
            RECEIVED -> 5
            UNKNOWN -> 99
    }

    companion object {
        fun fromInt(state: Int): MessageState =
            when(state) {
                1 -> SENT
                2 -> DELIVERED
                3 -> READ
                4 -> PENDING
                5 -> RECEIVED
                else -> UNKNOWN
        }
    }
}