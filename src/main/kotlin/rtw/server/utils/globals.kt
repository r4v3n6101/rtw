package rtw.server.utils

import net.minecraft.world.World
import rtw.server.data.DataRetriever
import rtw.server.data.impl.ScriptDataRetriever
import java.time.OffsetTime
import java.time.ZoneId

val rtwDataRetriever: DataRetriever by lazy { ScriptDataRetriever() }

fun World.setTimeFromZone(zone: ZoneId) {
    val time = OffsetTime.now(zone)
    worldTime = ((time.hour - 6) * 1000 + time.minute * 1000 / 60) % 24000L
}