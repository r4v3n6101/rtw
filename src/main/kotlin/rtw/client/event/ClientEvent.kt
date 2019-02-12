package rtw.client.event

import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge

class ClientEvent {

    init {
        MinecraftForge.EVENT_BUS.register(this)
        FMLCommonHandler.instance().bus().register(this)
    }

    /*@SubscribeEvent
    fun renderEvent(event: EntityViewRenderEvent.RenderFogEvent) {
        val prevFogStart = GL11.glGetFloat(GL_FOG_START)

        val d = event.farPlaneDistance / prevFogStart

        val fogStart = min(prevFogStart, clientRtwData.visibility)
        glFogf(GL_FOG_START, fogStart)
        glFogf(GL_FOG_END, fogStart * d)
    }*/
}