package rtw.common.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import rtw.common.util.gson.LocalTimeAdapter
import rtw.common.util.gson.ZoneIdAdapter
import java.time.LocalTime
import java.time.ZoneId

const val MODID = "rtw"
const val NAME = "Real Time & Weather"

val GSON: Gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<ZoneId>() {}.type, ZoneIdAdapter())
        .registerTypeAdapter(object : TypeToken<LocalTime>() {}.type, LocalTimeAdapter())
        .setPrettyPrinting()
        .create()


inline fun <reified T> fromJson(str: String): T = GSON.fromJson(str, T::class.java)

@Suppress("NOTHING_TO_INLINE")
inline fun toJson(t: Any): String = GSON.toJson(t)

/**
 * 0f if sunrise, 1f if sunset
 */
fun calculateSunPhase(sunrise: LocalTime, sunset: LocalTime, current: LocalTime): Float {
    return if (current.isAfter(sunrise) && current.isBefore(sunset)) {
        (current.nano - sunrise.nano).toFloat() / (sunset.nano - sunrise.nano).toFloat()
        // c in [a;b]
    } else {
        // c in [b;a]
        0.5f // TODO : Make it for night time
    }
}