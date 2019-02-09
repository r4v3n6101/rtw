package rtw.common.data

import java.time.LocalTime
import java.time.ZoneId

data class RTWData(
        val zoneId: ZoneId = ZoneId.systemDefault(), // 'nuff said
        val sunriseTime: LocalTime = LocalTime.MIN, // UTC+0
        val sunsetTime: LocalTime = LocalTime.MIN, // UTC+0
        val temperature: Float = 0F, // Celsius
        val visibility: Int = 0, // Meters
        val cloudiness: Int = 0 // Percentage
)