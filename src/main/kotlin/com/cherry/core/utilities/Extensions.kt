package com.cherry.core.utilities

import org.json.JSONObject

/**
 * Created by girish on 11/19/17.
 */

fun getJSONObjectOrNull(string: String?): JSONObject? {
    return try {
        JSONObject(string ?: return null)
    } catch (t: Throwable) {
        null
    }
}
