package com.cherry.core.controllers

import com.cherry.core.data.repositories.CoreDataRepository
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by girish on 11/12/17.
 */

class MessageController {

    fun postMessage(text: String, recipientId: String, token: String): Response<String> {
        val body = JSONObject()
        return CoreDataRepository.getNetworkDataRepository().postMessage(body).execute()
    }
}