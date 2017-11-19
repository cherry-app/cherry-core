package com.cherry.core.controllers

import com.cherry.core.Cherry
import com.cherry.core.data.repositories.CoreDataRepository
import com.google.gson.JsonObject
import retrofit2.Response

/**
 * Created by girish on 11/12/17.
 */

class MessageController {

    fun postMessage(text: String, recipientId: String, token: String): Response<String> {
        val body = JsonObject()
        return CoreDataRepository.getNetworkDataRepository().postMessage(Cherry.partnerId, body).execute()
    }
}