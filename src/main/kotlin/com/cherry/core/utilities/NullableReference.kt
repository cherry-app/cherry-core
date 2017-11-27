package com.cherry.core.utilities

/**
 * Created by girish on 11/27/17.
 */

class NullableReference<out T>(private val of: T?) {
    fun get(): T? = of
}