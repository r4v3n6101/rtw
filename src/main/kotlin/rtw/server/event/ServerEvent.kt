package rtw.server.event

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.terraingen.BiomeEvent
import rtw.common.ModMain
import rtw.common.util.toJson
import rtw.server.util.rtwDataRetriever
import rtw.server.util.setTimeFromZone
import java.time.LocalDate

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

    @SubscribeEvent
    fun biomeColor(event: BiomeEvent.BiomeColor) {
        val ld = LocalDate.now(ModMain.proxy.rtwData.zoneOffset)
        val month = ld.monthValue
        val biome = event.biome
        when (month) {
            1, 2, 12 -> {
                event.newColor = 9283496
                biome.temperature = 0.15f
                biome.setDisableRain().setEnableSnow()
            } // winter
            in 3..5 -> {
                event.newColor = 9284496
                biome.temperature = 1.0f
            } //spring
            in 6..8 -> {
                event.newColor = 9286496
                biome.temperature = 1.3f
            } // summer
            in 9..11 -> {
                event.newColor = 16421912
                biome.temperature = 0.75f
            } //fall
        }
    }

    private fun processRtwData(world: World) {
        world.setTimeFromZone(ModMain.proxy.rtwData.zoneOffset)
    }

    private fun syncRtwData() {
        ModMain.networkManager.sendToClients(0, toJson(ModMain.proxy.rtwData))
    }
}

