package rtw.common.world

import net.minecraft.world.WorldProvider
import rtw.client.util.clientRtwData
import rtw.common.util.calculateSunPhase
import java.time.LocalTime

class CustomWorldProvider : WorldProvider() {
    override fun getDimensionName() = "Overworld"

    override fun calculateCelestialAngle(time: Long, partialTicks: Float): Float {
        // TODO
        return (calculateSunPhase(clientRtwData.sunriseTime, clientRtwData.sunsetTime, LocalTime.now()) - 0.5f) / 2f
    }

    override fun getMoonPhase(time: Long): Int {
        return 3 // TODO
    }

    override fun getCurrentMoonPhaseFactor(): Float {
        return 0.5f // TODO
    }
}