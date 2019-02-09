package rtw.common.utils

import com.google.gson.GsonBuilder

const val MODID = "rtw"
const val NAME = "Real Time & Weather"

val GSON = GsonBuilder().setPrettyPrinting().create()

inline fun <reified T> fromJson(str: String): T = GSON.fromJson(str, T::class.java)

@Suppress("NOTHING_TO_INLINE")
inline fun toJson(t: Any): String = GSON.toJson(t)
