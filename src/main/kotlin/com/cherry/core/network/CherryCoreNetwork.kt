package com.cherry.core.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by girish on 11/12/17.
 */

interface CherryCoreNetwork {

    @Headers(
            "Content-Type: application/json"
    )
    @POST(ApiRoutes.SIGN_UP)
    fun signUp(@Header("Cherry-Partner-ID") partnerId: String, @Body body: JsonObject): Call<String>

    @POST(ApiRoutes.VERIFY)
    fun verifyOtp(@Header("Cherry-Partner-ID") partnerId: String, @Body body: JsonObject): Call<String>

    @POST(ApiRoutes.RESEND_OTP)
    fun resendOtp(@Header("Cherry-Partner-ID") partnerId: String, @Body body: JsonObject): Call<String>

    @POST(ApiRoutes.MESSAGE)
    fun postMessage(@Header("Cherry-Partner-ID") partnerId: String, @Header("Cherry-UID") uid: String , @Header("Cherry-Auth-Token") authToken: String, @Body body: JsonObject): Call<String>

    @POST(ApiRoutes.SYNC_CONTACTS)
    fun syncContacts(@Header("Cherry-Partner-ID") partnerId: String, @Header("Cherry-UID") uid: String , @Header("Cherry-Auth-Token") authToken: String, @Body body: JsonObject): Call<String>

    @POST(ApiRoutes.FCM_TOKEN)
    fun updateToken(@Header("Cherry-Partner-ID") partnerId: String, @Header("Cherry-UID") uid: String , @Header("Cherry-Auth-Token") authToken: String, @Body body: JsonObject): Call<String>
}