package com.cherry.core.controllers

import com.cherry.core.Cherry
import com.cherry.core.data.repositories.CoreDataRepository
import com.google.gson.JsonObject
import retrofit2.Response

/**
 * Created by girish on 11/19/17.
 */

class SessionController {

    fun signUp(phoneNumber: String, name: String): Response<String> {
        val body = JsonObject()
        body.addProperty("phone_number", phoneNumber)
        body.addProperty("name", name)
        return CoreDataRepository.getNetworkDataRepository().signUp(Cherry.partnerId, body).execute()
    }

    fun verifyOtp(otp: String, loginToken: String): Response<String> {
        val body = JsonObject()
        body.addProperty("otp", otp)
        body.addProperty("login_token", loginToken)
        return CoreDataRepository.getNetworkDataRepository().verifyOtp(Cherry.partnerId, body).execute()
    }

    fun resendOtp(phoneNumber: String, loginToken: String): Response<String> {
        val body = JsonObject()
        body.addProperty("phone_number", phoneNumber)
        body.addProperty("login_token", loginToken)
        return CoreDataRepository.getNetworkDataRepository().resendOtp(Cherry.partnerId, body).execute()
    }
}