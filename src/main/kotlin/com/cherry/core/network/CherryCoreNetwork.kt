package com.cherry.core.network

import org.json.JSONObject
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
    fun signUp(@Header("Cherry-Partner-ID") partnerId: String, @Body body: JSONObject): Call<String>

    @POST(ApiRoutes.VERIFY)
    fun verifyOtp(@Header("Cherry-Partner-ID") partnerId: String, @Body body: JSONObject): Call<String>

    @POST(ApiRoutes.MESSAGE)
    fun postMessage(@Header("Cherry-Partner-ID") partnerId: String, @Body body: JSONObject): Call<String>
}