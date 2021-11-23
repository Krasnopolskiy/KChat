package com.models

import java.util.concurrent.atomic.AtomicInteger

open class Model {
    companion object {
        val latestId = AtomicInteger(0)
    }

    val id = latestId.getAndIncrement()
}
