package com.example.tccmobile.helpers

import kotlin.random.Random

fun generateTicketId(): Int {
    return Random.nextInt(1000000, 10000000)
}