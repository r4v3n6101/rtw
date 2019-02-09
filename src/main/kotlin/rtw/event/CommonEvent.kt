package rtw.event

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraftforge.common.MinecraftForge
import rtw.globalTimeZone
import rtw.setTimeFromZone

class CommonEvent {
    init {
        MinecraftForge.EVENT_BUS.register(this)
        FMLCommonHandler.instance().bus().register(this)
    }

    @SubscribeEvent
    fun worldTick(event: TickEvent.WorldTickEvent) {
        setTimeFromZone(event.world, globalTimeZone)
    }
}