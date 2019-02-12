package rtw.server.data.impl

import rtw.server.data.DataRetriever
import java.nio.file.Files
import java.nio.file.Path
import javax.script.Invocable
import javax.script.ScriptEngineManager

class ScriptDataRetriever(scriptDir: Path) : DataRetriever {

    private var _retriever: DataRetriever = DefaultDataRetriever

    init {
        Files.walk(scriptDir).filter {
            Files.isRegularFile(it) &&
                    it.toFile().nameWithoutExtension == "script"
        }.forEach { scriptFile ->
            println("Found: $scriptFile")
            val extension = scriptFile.toFile().extension
            val scriptEngine = ScriptEngineManager().getEngineByExtension(extension)
            if (scriptEngine is Invocable) {
                scriptEngine.eval(Files.newBufferedReader(scriptFile))
                _retriever = scriptEngine.getInterface(DataRetriever::class.java)
                println("Appropriate script, extension=.$extension")
            } else {
                println("ScriptEngine isn't invocable, skip")
            }
        }
    }

    override fun retrieve() = _retriever.retrieve()
}