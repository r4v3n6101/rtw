package rtw.server.event

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import rtw.common.ModMain
import rtw.common.data.RTWData
import rtw.common.util.toJson
import rtw.server.util.rtwDataRetriever
import rtw.server.util.setTimeFromZone

class ServerEvent {
    init {
        MinecraftForge.EVENT_BUS.register(this)
        FMLCommonHandler.instance().bus().register(this)
    }

    @SubscribeEvent
    fun worldTick(event: TickEvent.WorldTickEvent) {
        if (!event.world.isRemote) {
            val rtwData = rtwDataRetriever.retrieve()
            processRtwData(event.world, rtwData)
            syncRtwData(rtwData)
        }
    }

    private fun processRtwData(world: World, rtwData: RTWData) {
        world.setTimeFromZone(rtwData.zoneOffset)

    }

    private fun syncRtwData(rtwData: RTWData) {
        ModMain.networkManager.sendToClients(0, toJson(rtwData))
    }
}

