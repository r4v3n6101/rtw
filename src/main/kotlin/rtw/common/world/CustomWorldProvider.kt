package rtw.common.world

import net.minecraft.world.WorldProvider
import rtw.common.ModMain
import rtw.common.util.getSolarHourAngle

class CustomWorldProvider : WorldProvider() {
    override fun getDimensionName() = "Overworld"

    override fun calculateCelestialAngle(time: Long, partialTicks: Float) =
            (getSolarHourAngle(ModMain.proxy.rtwData.longitude.toDouble()) / 360).toFloat()

    override fun getMoonPhase(time: Long) = 0
    override fun getCurrentMoonPhaseFactor(): Float = 0f
}