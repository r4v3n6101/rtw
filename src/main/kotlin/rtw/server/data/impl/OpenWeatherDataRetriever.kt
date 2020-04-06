package rtw.server.data.impl

import com.google.gson.JsonParser
import rtw.common.data.RTWData
import rtw.server.data.DataRetriever
import java.net.URL
import java.time.Duration
import java.time.ZoneOffset
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

class OpenWeatherDataRetriever(
        private val offset: ZoneOffset = ZoneOffset.of("+3"),
        updateDelay: Long = Duration.ofMinutes(10).toMillis(),
        apiKey: String = "0b26f2a80235ec3a9986ff95d551e24f",
        city: String = "moscow,ru"
) : DataRetriever {

    private val weatherUrl = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric")
    private var rtwData = AtomicReference(DefaultDataRetriever.retrieve())
    private val threadPool = Executors.newScheduledThreadPool(5)

    init {
        threadPool.scheduleWithFixedDelay(::updateData, 0, updateDelay, TimeUnit.MILLISECONDS)
    }

    private fun updateData() {
        val response = weatherUrl.readText()
        println("Get response: $response")
        val root = JsonParser().parse(response).asJsonObject

        val coord = root.getAsJsonObject("coord")
        val main = root.getAsJsonObject("main")
        val clouds = root.getAsJsonObject("clouds")

        rtwData.set(RTWData(
                latitude = coord["lat"].asFloat,
                longitude = coord["lon"].asFloat,
                zoneOffset = offset,
                temperature = main["temp"].asFloat,
                visibility = root["visibility"].asInt.toFloat(),
                cloudiness = clouds["all"].asInt.toFloat()
        ))
    }

    override fun retrieve(): RTWData = rtwData.get()
}