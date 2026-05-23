package pl.polutek.suweren.data

import java.time.Duration
import java.time.Instant
import kotlin.math.max
import kotlin.math.roundToInt

object ScoringLogic {
    fun calculatePIndex(data: SuwerenData): Int {
        val now = Instant.now()

        // Nicotine
        val nicotineHours = Duration.between(data.nicotine.lastReset, now).toHours()
        val nicotineMult = 1f + (data.nicotine.yearsOfAddiction / 18f)
        val nicotinePoints = nicotineHours * 0.1f * nicotineMult

        // THC
        val thcHours = Duration.between(data.thc.lastReset, now).toHours()
        val thcMult = 1f + (data.thc.yearsOfAddiction / 18f)
        val thcPoints = thcHours * 0.2f * thcMult

        // No-Fap
        val noFapDays = Duration.between(data.noFap.lastReset, now).toDays()
        val noFapMult = 1f + (data.noFap.yearsOfAddiction / 18f)
        val noFapPoints = noFapDays * 5f * noFapMult

        // Running
        val runningBonus = max(0f, data.runningKmThisWeek - 35f) * 2f

        return (nicotinePoints + thcPoints + noFapPoints + runningBonus).roundToInt()
    }
}
