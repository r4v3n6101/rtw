package rtw.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

/*fun getDayFraction(time: LocalTime) = time.toSecondOfDay().toDouble() / Duration.ofDays(1).seconds

fun getDaysSinceNewYear(ldt: LocalDateTime) = (ldt.dayOfYear - 1) + getDayFraction(ldt.toLocalTime())

fun getEoT(ld: LocalDate): Double {
    val n = ld.dayOfYear
    val b = 2 * PI * (n - 81) / 365
    return 9.87 * sin(2 * b) - 7.53 * cos(b) - 1.5 * sin(b)
}


fun getSunDeclination(ldt: LocalDateTime): Double {
    val n = getDaysSinceNewYear(ldt)
    val e = toRadians(-23.44)
    val ndc = toRadians((360.0 / 365.0) * (n + 10))
    return e * cos(ndc) // TODO  : Test via www.jgiesen.de/astro/suncalc/
}

fun getHourAngle(time: LocalTime) =
        (getDayFraction(time) - 0.5) * 2 * PI // TODO : It's not solar time

fun getSunAltitude(lat: Double, decl: Double, hourAng: Double) =
        asin(sin(lat) * sin(decl) + cos(lat) * cos(decl) * cos(hourAng))

fun getZonedSunAltitude(lat: Double, zoneId: ZoneId): Double {
    val dt = ZonedDateTime.now(zoneId)
    val h = getHourAngle(dt.toLocalTime())
    val d = getSunDeclination(dt.toLocalDateTime())
    println(getEoT(dt.toLocalDate()))
    return getSunAltitude(lat, d, h)
}*/

fun getEoT(n: Int): Double {
    val b = 2 * PI * (n - 81) / 365
    return 9.87 * sin(2 * b) - 7.53 * cos(b) - 1.5 * sin(b)
}

/**
 * @param longitude degrees
 * @return degrees
 */
fun getSolarHourAngle(longitude: Double): Double {
    val ltd = LocalDateTime.now(ZoneOffset.UTC)
    val eot = getEoT(ltd.dayOfYear)
    val solarTime = ltd.toLocalTime().toSecondOfDay() + (longitude * 4 + eot) * 60
    return (solarTime - 12 * 60 * 60) / (24 * 60 * 60) * 360
}

const val LUNAR_MONTH = 30 // For
val MOON_PHASES_NAMES = arrayOf(
        "New moon", "Waxing Crescent", "First Quarter", "Waxing Gibbous",
        "Full moon", "Waning Gibbous", "Third Quarter", "Waning Crescent"
)

fun getDaysSinceNewMoon(ld: LocalDate) = if (ld.monthValue == 1 || ld.monthValue == 2)
    ((ld.year - 1) % 19 * 11 + 9 + ld.monthValue + ld.dayOfMonth) % 30
else
    (ld.year % 19 * 11 + ld.monthValue - 3 + ld.dayOfMonth) % 30

fun getMoonPhaseNumber(n: Int) = round(8.0 * n / LUNAR_MONTH).toInt() % 8

fun getMoonIllumination(n: Int) = sin(PI * n / LUNAR_MONTH)