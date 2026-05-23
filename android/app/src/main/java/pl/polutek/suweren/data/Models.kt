package pl.polutek.suweren.data

import java.time.Instant

data class AddictionState(
    val lastReset: Instant = Instant.now(),
    val yearsOfAddiction: Int = 0
)

data class SuwerenData(
    val nicotine: AddictionState = AddictionState(yearsOfAddiction = 18),
    val thc: AddictionState = AddictionState(),
    val noFap: AddictionState = AddictionState(),
    val runningKmThisWeek: Float = 0f
)
