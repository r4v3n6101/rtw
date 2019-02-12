package rtw.common.util.gson

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private val localTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    override fun serialize(src: LocalTime, typeOfSrc: Type, context: JsonSerializationContext): JsonPrimitive =
            JsonPrimitive(localTimeFormatter.format(src))

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalTime =
            localTimeFormatter.parse(json.asString, LocalTime::from)
}
