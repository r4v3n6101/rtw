package rtw.common

import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.common.DimensionManager
import rtw.common.data.RTWData
import rtw.common.world.CustomWorldProvider
import rtw.server.command.CommandRtw
import rtw.server.event.ServerEvent

open class CommonProxy {

    var rtwData = RTWData()

    open fun preInit(event: FMLPreInitializationEvent) {
    }

    open fun init(event: FMLInitializationEvent) {
        DimensionManager.unregisterProviderType(0)
        DimensionManager.registerProviderType(0, CustomWorldProvider::class.java, true)
        ServerEvent() // Init event
    }

    open fun postInit(event: FMLPostInitializationEvent) {
    }

    open fun serverStarting(event: FMLServerStartingEvent) {
        event.registerServerCommand(CommandRtw())
    }
}