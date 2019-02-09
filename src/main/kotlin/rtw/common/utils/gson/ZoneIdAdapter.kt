package rtw.common.utils.gson

import com.google.gson.*
import java.lang.reflect.Type
import java.time.ZoneId

class ZoneIdAdapter : JsonSerializer<ZoneId>, JsonDeserializer<ZoneId> {

    override fun serialize(src: ZoneId, typeOfSrc: Type, context: JsonSerializationContext): JsonPrimitive =
            JsonPrimitive(src.id)

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ZoneId =
            ZoneId.of(json.asString)
}
