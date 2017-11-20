package com.cherry.core.utilities

import com.google.gson.JsonArray
import org.json.JSONArray
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

fun <T: AutoCloseable?, R> T.useAs(block: T.() -> R): R = use(block)

fun Iterable<String>.toJsonArray(): JsonArray = JsonArray().apply { this@toJsonArray.forEach { add(it) } }

fun JSONArray.toArrayList(): ArrayList<String> {
    val list = ArrayList<String>()
    for (i in 0 until list.size) {
        list.add(optString(i) ?: continue)
    }
    return list
}