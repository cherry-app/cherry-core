package com.cherry.core.models

import java.io.Serializable

/**
 * Created by girish on 11/12/17.
 */

enum class RecipientType: Serializable {
    INDIVIDUAL, GROUP, UNKNOWN;

    fun asInt(): Int  =
        when(this) {
        INDIVIDUAL -> 1
        GROUP -> 2
        UNKNOWN -> 99
    }

    companion object {
        fun fromInt(type: Int): RecipientType =
            when(type) {
                1 -> INDIVIDUAL
                2 -> GROUP
                else -> UNKNOWN
        }
    }
}