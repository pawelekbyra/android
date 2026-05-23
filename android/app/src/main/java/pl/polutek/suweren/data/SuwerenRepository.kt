package pl.polutek.suweren.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant

private val Context.dataStore by preferencesDataStore(name = "suweren_prefs")

class SuwerenRepository(private val context: Context) {

    private object Keys {
        val NICOTINE_RESET = stringPreferencesKey("nicotine_reset")
        val NICOTINE_YEARS = intPreferencesKey("nicotine_years")
        val THC_RESET = stringPreferencesKey("thc_reset")
        val THC_YEARS = intPreferencesKey("thc_years")
        val NOFAP_RESET = stringPreferencesKey("nofap_reset")
        val NOFAP_YEARS = intPreferencesKey("nofap_years")
        val RUNNING_KM = floatPreferencesKey("running_km")
    }

    val suwerenData: Flow<SuwerenData> = context.dataStore.data.map { prefs ->
        SuwerenData(
            nicotine = AddictionState(
                lastReset = prefs[Keys.NICOTINE_RESET]?.let { Instant.parse(it) } ?: Instant.now(),
                yearsOfAddiction = prefs[Keys.NICOTINE_YEARS] ?: 18
            ),
            thc = AddictionState(
                lastReset = prefs[Keys.THC_RESET]?.let { Instant.parse(it) } ?: Instant.now(),
                yearsOfAddiction = prefs[Keys.THC_YEARS] ?: 0
            ),
            noFap = AddictionState(
                lastReset = prefs[Keys.NOFAP_RESET]?.let { Instant.parse(it) } ?: Instant.now(),
                yearsOfAddiction = prefs[Keys.NOFAP_YEARS] ?: 0
            ),
            runningKmThisWeek = prefs[Keys.RUNNING_KM] ?: 0f
        )
    }

    suspend fun resetNicotine() {
        context.dataStore.edit { it[Keys.NICOTINE_RESET] = Instant.now().toString() }
    }

    suspend fun resetThc() {
        context.dataStore.edit { it[Keys.THC_RESET] = Instant.now().toString() }
    }

    suspend fun resetNoFap() {
        context.dataStore.edit { it[Keys.NOFAP_RESET] = Instant.now().toString() }
    }

    suspend fun addRunningKm(km: Float) {
        context.dataStore.edit {
            val current = it[Keys.RUNNING_KM] ?: 0f
            it[Keys.RUNNING_KM] = current + km
        }
    }

    suspend fun setLastReset(type: String, instant: Instant) {
        context.dataStore.edit {
            when (type) {
                "nicotine" -> it[Keys.NICOTINE_RESET] = instant.toString()
                "thc" -> it[Keys.THC_RESET] = instant.toString()
                "noFap" -> it[Keys.NOFAP_RESET] = instant.toString()
            }
        }
    }
}
