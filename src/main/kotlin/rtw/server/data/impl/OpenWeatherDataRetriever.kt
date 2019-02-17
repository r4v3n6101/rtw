package rtw.server.data.impl

import com.google.gson.JsonParser
import rtw.common.data.RTWData
import rtw.server.data.DataRetriever
import java.io.IOException
import java.net.URL
import java.time.Duration
import java.time.ZoneOffset
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class OpenWeatherDataRetriever(
        private val updateDelay: Long = Duration.ofMinutes(1).toMillis(),
        private val offset: ZoneOffset = ZoneOffset.of("-5"),
        apiKey: String = "0b26f2a80235ec3a9986ff95d551e24f",
        city: String = "new%20york,us"
) : DataRetriever {
    private val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"
    private val cachedRtwData: CompletableFuture<RTWData> = CompletableFuture()

    private var lastConfigUpdate: Long = 0

    @Throws(IOException::class)
    private fun requestData(): RTWData {
        val response = URL(weatherUrl).readText()
        val root = JsonParser().parse(response).asJsonObject

        val coord = root.getAsJsonObject("coord")
        val main = root.getAsJsonObject("main")
        val clouds = root.getAsJsonObject("clouds")

        return RTWData(
                latitude = coord["lat"].asFloat,
                longitude = coord["lon"].asFloat,
                zoneOffset = offset,
                temperature = main["temp"].asFloat,
                visibility = root["visibility"].asInt.toFloat(),
                cloudiness = clouds["all"].asInt.toFloat()
        )
    }

    override fun retrieve(): RTWData {
        if (lastConfigUpdate + updateDelay < System.currentTimeMillis()) {
            Executors.newCachedThreadPool().submit {
                try {
                    val data = requestData()
                    cachedRtwData.complete(data)
                    println("Data updated: $data")
                } catch (t: Throwable) {
                    cachedRtwData.completeExceptionally(t)
                    t.printStackTrace()
                }
            }

            lastConfigUpdate = System.currentTimeMillis()
        }
        return cachedRtwData.getNow(DefaultDataRetriever.retrieve())
    }
}

