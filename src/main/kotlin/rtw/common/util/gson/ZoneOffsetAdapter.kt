package rtw.common.util.gson

import com.google.gson.*
import java.lang.reflect.Type
import java.time.ZoneId
import java.time.ZoneOffset

class ZoneOffsetAdapter : JsonSerializer<ZoneOffset>, JsonDeserializer<ZoneId> {

    override fun serialize(src: ZoneOffset, typeOfSrc: Type, context: JsonSerializationContext): JsonPrimitive =
            JsonPrimitive(src.id)

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ZoneOffset =
            ZoneOffset.of(json.asString)
}
