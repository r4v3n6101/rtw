package rtw.client.event

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.EntityViewRenderEvent
import net.minecraftforge.common.MinecraftForge
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import rtw.common.ModMain
import kotlin.math.min

class ClientEvent {

    init {
        MinecraftForge.EVENT_BUS.register(this)
        FMLCommonHandler.instance().bus().register(this)
    }

    @SubscribeEvent
    fun renderEvent(event: EntityViewRenderEvent.RenderFogEvent) {
        val prevFogStart = GL11.glGetFloat(GL_FOG_START)

        val d = event.farPlaneDistance / prevFogStart

        val fogStart = min(prevFogStart, ModMain.proxy.rtwData.visibility / 10f) // div by 10 should be ok
        glFogf(GL_FOG_START, fogStart)
        glFogf(GL_FOG_END, fogStart * d)
    }
}