package com.raghav.samplekmm

import com.raghav.samplekmm.cache.Database
import com.raghav.samplekmm.cache.DatabaseDriverFactory
import com.raghav.samplekmm.entity.RocketLaunch
import com.raghav.samplekmm.network.SpaceXApi

/**
 * wrapper over the class SpaceXApi used by both Android and IOS for networking
 */
class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}
