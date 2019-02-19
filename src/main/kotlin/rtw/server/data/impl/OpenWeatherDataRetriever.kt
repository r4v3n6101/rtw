package rtw.server.data.impl

import com.google.gson.JsonParser
import rtw.common.data.RTWData
import rtw.server.data.DataRetriever
import java.net.URL
import java.time.Duration
import java.time.ZoneOffset
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

class OpenWeatherDataRetriever(
        private val updateDelay: Long = Duration.ofMinutes(10).toMillis(),
        private val offset: ZoneOffset = ZoneOffset.of("+3"),
        apiKey: String = "0b26f2a80235ec3a9986ff95d551e24f",
        city: String = "moscow,ru"
) : DataRetriever {

    private val weatherUrl = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric")
    private var futureRtwData: Future<RTWData?> = FutureTask { null }
    private var lastConfigUpdate = 0L

    /**
     * @return null if error ocured, rtw data otherwise
     */
    private fun requestData() = try {
        val response = weatherUrl.readText()
        println("Get response: $response")
        val root = JsonParser().parse(response).asJsonObject

        val coord = root.getAsJsonObject("coord")
        val main = root.getAsJsonObject("main")
        val clouds = root.getAsJsonObject("clouds")

        RTWData(
                latitude = coord["lat"].asFloat,
                longitude = coord["lon"].asFloat,
                zoneOffset = offset,
                temperature = main["temp"].asFloat,
                visibility = root["visibility"].asInt.toFloat(),
                cloudiness = clouds["all"].asInt.toFloat()
        )
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }

    override fun retrieve(): RTWData {
        if (lastConfigUpdate + updateDelay < System.currentTimeMillis()) {
            futureRtwData = Executors.newCachedThreadPool().submit(::requestData)
            lastConfigUpdate = System.currentTimeMillis()
        }
        return futureRtwData.get() ?: DefaultDataRetriever.retrieve()
    }
}
