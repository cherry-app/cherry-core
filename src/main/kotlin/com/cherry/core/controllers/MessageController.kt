package com.cherry.core.controllers

import com.cherry.core.data.repositories.CoreDataRepository
import retrofit2.Response

/**
 * Created by girish on 11/12/17.
 */

class MessageController {

    fun postMessage(text: String, recipientId: String, token: String): Response<String> {
        val body = HashMap<String, String>()
        return CoreDataRepository.getNetworkDataRepository().postMessage(body).execute()
    }
}