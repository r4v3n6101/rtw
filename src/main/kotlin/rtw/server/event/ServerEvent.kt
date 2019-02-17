package rtw.server.event

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import rtw.common.ModMain
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
            ModMain.proxy.rtwData = rtwDataRetriever.retrieve()
            processRtwData(event.world)
            syncRtwData()
        }
    }

    private fun processRtwData(world: World) {
        world.setTimeFromZone(ModMain.proxy.rtwData.zoneOffset)
    }

    private fun syncRtwData() {
        ModMain.networkManager.sendToClients(0, toJson(ModMain.proxy.rtwData))
    }
}

