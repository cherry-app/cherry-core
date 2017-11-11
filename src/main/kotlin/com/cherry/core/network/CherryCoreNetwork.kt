package com.cherry.core.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by girish on 11/12/17.
 */

interface CherryCoreNetwork {

    @GET(ApiRoutes.SIGN_IN)
    fun signIn(@Query("msisdn") phoneNumber: String, @Query("partnerId") partnerId: String): Call<String>

    @POST(ApiRoutes.VERIFY)
    fun verifyOtp(@Body body: Map<String, String>): Call<String>

    @POST(ApiRoutes.MESSAGE)
    fun postMessage(@Body body: Map<String, String>): Call<String>
}