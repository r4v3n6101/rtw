package rtw.server.data.impl

import rtw.server.data.DataRetriever
import java.nio.file.Files
import java.nio.file.Paths
import javax.script.Invocable
import javax.script.ScriptEngineManager

class ScriptDataRetriever(
        path: String = System.getProperty("rtw.script.path", "script.rtw"),
        scriptEngineName: String = System.getProperty("rtw.script.enginename", "nashorn")
) : DataRetriever {

    private var scriptRetriever: DataRetriever = DefaultDataRetriever

    init {
        val scriptPath = Paths.get(path)

        if (Files.exists(scriptPath)) {
            val scriptCode = String(Files.readAllBytes(scriptPath))
            val scriptEngine = ScriptEngineManager().getEngineByName(scriptEngineName)
            scriptEngine.eval(scriptCode)
            if (scriptEngine is Invocable)
                scriptRetriever = scriptEngine.getInterface(DataRetriever::class.java)
        } else {
            println("Script not found at ${scriptPath.toAbsolutePath()}, switch to default")
        }
    }

    override fun retrieve() = scriptRetriever.retrieve()
}