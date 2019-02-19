package rtw.common.world

import net.minecraft.world.WorldProvider
import rtw.common.ModMain
import rtw.common.util.getDaysSinceNewMoon
import rtw.common.util.getMoonIllumination
import rtw.common.util.getMoonPhaseNumber
import rtw.common.util.getSolarHourAngle
import java.time.LocalDate

class CustomWorldProvider : WorldProvider() {
    override fun getDimensionName() = "Overworld"

    override fun calculateCelestialAngle(time: Long, partialTicks: Float): Float {
        val lon = ModMain.proxy.rtwData.longitude
        val solarAngle = getSolarHourAngle(lon.toDouble())
        return (solarAngle / 360.0).toFloat()
    }

    override fun getMoonPhase(time: Long): Int {
        val ld = LocalDate.now(ModMain.proxy.rtwData.zoneOffset)
        val n = getDaysSinceNewMoon(ld)
        val i = getMoonPhaseNumber(n)
        return when (i) {
            in 0..3 -> i + 4
            in 4..7 -> i - 4
            else -> i
        }
    }

    override fun getCurrentMoonPhaseFactor(): Float {
        val ld = LocalDate.now(ModMain.proxy.rtwData.zoneOffset)
        val n = getDaysSinceNewMoon(ld)
        return getMoonIllumination(n).toFloat()
    }
}