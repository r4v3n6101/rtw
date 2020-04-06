package rtw.server.data.impl

import rtw.server.data.DataRetriever
import java.nio.file.Files
import java.nio.file.Path
import javax.script.Invocable
import javax.script.ScriptEngineManager

class ScriptDataRetriever(scriptPath: Path) : DataRetriever {

    private var _retriever: DataRetriever = DefaultDataRetriever

    init {
        val extension = scriptPath.toFile().extension
        val scriptEngine = ScriptEngineManager().getEngineByExtension(extension)
        if (scriptEngine is Invocable) {
            Files.newBufferedReader(scriptPath).use(scriptEngine::eval)
            _retriever = scriptEngine.getInterface(DataRetriever::class.java)
            println("Appropriate script, extension=.$extension")
        } else {
            println("ScriptEngine isn't invocable, skip")
        }
    }

    override fun retrieve() = _retriever.retrieve()
}