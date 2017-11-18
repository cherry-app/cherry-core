package com.cherry.core.controllers

import com.cherry.core.data.repositories.CoreDataRepository
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by girish on 11/19/17.
 */

class SessionController {

    fun signUp(phoneNumber: String, name: String): Response<String> {
        val body = JSONObject()
        body.put("phone_number", phoneNumber)
        body.put("name", name)
        return CoreDataRepository.getNetworkDataRepository().signUp(body).execute()
    }

    fun verifyOtp(otp: String, loginToken: String): Response<String> {
        val body = JSONObject()
        body.put("otp", otp)
        body.put("login_token", loginToken)
        return CoreDataRepository.getNetworkDataRepository().verifyOtp(body).execute()
    }
}