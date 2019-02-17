package rtw.server.util

import net.minecraft.world.World
import rtw.server.data.DataRetriever
import rtw.server.data.impl.OpenWeatherDataRetriever
import java.time.OffsetTime
import java.time.ZoneOffset

val rtwDataRetriever: DataRetriever by lazy {
    OpenWeatherDataRetriever()
}

fun World.setTimeFromZone(offset: ZoneOffset) {
    val time = OffsetTime.now(offset).minusHours(6)
    worldTime = ((time.hour + time.minute / 60f) * 1000f).toLong() % 24000L
}