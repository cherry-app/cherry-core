package com.cherry.core.network

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by girish on 11/12/17.
 */

interface CherryCoreNetwork {

    @POST(ApiRoutes.SIGN_UP)
    fun signUp(@Body body: JSONObject): Call<String>

    @POST(ApiRoutes.VERIFY)
    fun verifyOtp(@Body body: JSONObject): Call<String>

    @POST(ApiRoutes.MESSAGE)
    fun postMessage(@Body body: JSONObject): Call<String>
}