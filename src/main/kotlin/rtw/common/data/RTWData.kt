package rtw.common.data

import java.time.ZoneOffset

data class RTWData(
        val latitude: Float = 0f, // Degress
        val longitude: Float = 0f, //Degrees
        val zoneOffset: ZoneOffset = ZoneOffset.UTC, // 'nuff said

        val temperature: Float = 20f, // Celsius
        val visibility: Float = Float.MAX_VALUE, // Meters
        val cloudiness: Float = 0.5f // Percentage / 100
)