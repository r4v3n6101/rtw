package rtw

import net.minecraft.world.World
import java.time.OffsetTime
import java.time.ZoneId

fun setTimeFromZone(world: World, zone: ZoneId) {
    val offsetTime = OffsetTime.now(zone)
    world.worldTime = ((offsetTime.hour - 6) * 1000 + offsetTime.minute * 1000 / 60) % 24000L
}