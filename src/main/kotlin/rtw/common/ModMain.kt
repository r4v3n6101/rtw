package rtw.common

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import network.NetworkManager
import rtw.common.network.RTWPacketCallback
import rtw.common.utils.MODID
import rtw.common.utils.NAME

@Mod(modid = MODID, name = NAME, version = "NO-VERSION")
class ModMain {
    companion object {
        @SidedProxy(serverSide = "rtw.common.CommonProxy", clientSide = "rtw.client.ClientProxy")
        lateinit var proxy: CommonProxy

        val networkManager = NetworkManager(MODID + "_network", RTWPacketCallback)
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        proxy.preInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        proxy.init(event)
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        proxy.postInit(event)
    }

    @Mod.EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        proxy.serverStarting(event)
    }
}