package rtw

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import rtw.commands.CommandRtime
import rtw.event.CommonEvent

@Mod(modid = "rtw", name = "Real Time & Weather", version = "NO-VERSION")
class ModMain {

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        CommonEvent() // Init event
    }

    @Mod.EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        event.registerServerCommand(CommandRtime()) // Register command
    }
}