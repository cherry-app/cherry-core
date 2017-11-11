package com.cherry.core.data.repositories

import android.arch.persistence.room.Room
import android.content.Context
import com.cherry.core.models.persistence.CherryCoreDatabase
import com.cherry.core.network.ApiRoutes
import com.cherry.core.network.CherryCoreNetwork
import retrofit2.Retrofit

/**
 * Created by girish on 11/12/17.
 */

object CoreDataRepository {

    @Volatile private var DATABASE_INSTANCE: CherryCoreDatabase? = null

    @Volatile private var NETWORK_INSTANCE: CherryCoreNetwork? = null

    fun getLocalDataRepository(context: Context): CherryCoreDatabase {
        return DATABASE_INSTANCE ?: Room.databaseBuilder(context, CherryCoreDatabase::class.java, "cherry-core.db").build().also { DATABASE_INSTANCE = it }
    }

    fun getNetworkDataRepository(): CherryCoreNetwork {
        return NETWORK_INSTANCE ?: Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL).build().create(CherryCoreNetwork::class.java).also { NETWORK_INSTANCE = it }
    }
}