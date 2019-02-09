package rtw.common

import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import rtw.server.commands.CommandRtime
import rtw.server.event.ServerEvent

open class CommonProxy {

    open fun preInit(event: FMLPreInitializationEvent) {
        ServerEvent() // Init event
    }

    open fun init(event: FMLInitializationEvent) {
    }

    open fun postInit(event: FMLPostInitializationEvent) {
    }

    open fun serverStarting(event: FMLServerStartingEvent) {
        event.registerServerCommand(CommandRtime())
    }
}