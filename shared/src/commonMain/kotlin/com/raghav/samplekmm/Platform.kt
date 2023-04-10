package com.raghav.samplekmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform