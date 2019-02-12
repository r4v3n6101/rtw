package rtw.common.data

import java.time.LocalTime
import java.time.ZoneOffset

data class RTWData(
        val zoneOffset: ZoneOffset = ZoneOffset.UTC, // 'nuff said
        val sunriseTime: LocalTime = LocalTime.of(6, 0), // UTC
        val sunsetTime: LocalTime = LocalTime.of(22, 0), // UTC
        val temperature: Float = 20f, // Celsius
        val visibility: Float = Float.MAX_VALUE, // Meters
        val cloudiness: Float = 50f // Percentage
)