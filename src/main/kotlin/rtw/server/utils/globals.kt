package rtw.server.utils

import net.minecraft.world.World
import rtw.server.data.DataRetriever
import rtw.server.data.impl.ScriptDataRetriever
import java.time.OffsetTime
import java.time.ZoneId

val rtwDataRetriever: DataRetriever by lazy { ScriptDataRetriever() }

fun World.setTimeFromZone(zone: ZoneId) {
    val time = OffsetTime.now(zone).minusHours(6)
    worldTime = ((time.hour + time.minute / 60f) * 1000f).toLong() % 24000L
}