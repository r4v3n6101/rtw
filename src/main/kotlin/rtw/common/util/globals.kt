package rtw.common.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import rtw.common.util.gson.LocalTimeAdapter
import rtw.common.util.gson.ZoneOffsetAdapter
import java.time.LocalTime
import java.time.ZoneOffset

const val MODID = "rtw"
const val NAME = "Real Time & Weather"

/*
                               /----\
                       -------/      \
                      /               \
                     /                |
   -----------------/                  --------\
   ----------------------------------------------
*/

val GSON: Gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<ZoneOffset>() {}.type, ZoneOffsetAdapter())
        .registerTypeAdapter(object : TypeToken<LocalTime>() {}.type, LocalTimeAdapter())
        .setPrettyPrinting()
        .create()


inline fun <reified T> fromJson(str: String): T = GSON.fromJson(str, T::class.java)

@Suppress("NOTHING_TO_INLINE")
inline fun toJson(t: Any): String = GSON.toJson(t)