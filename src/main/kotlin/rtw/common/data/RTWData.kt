package rtw.common.data

import java.time.ZoneId

data class RTWData(
        val zoneId: String = ZoneId.systemDefault().id, // 'nuff said
        val sunriseTime: Long = 0, // UTC
        val sunsetTime: Long = 0, // UTC
        val temperature: Float = 0F, // Celsius
        val visibility: Int = 0, // Meters
        val cloudiness: Int = 0 // Percentage
)