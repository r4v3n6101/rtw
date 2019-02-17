package rtw.common.network

import cpw.mods.fml.common.network.ByteBufUtils
import io.netty.buffer.ByteBuf
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.World
import network.PacketCallback
import rtw.common.ModMain
import rtw.common.util.fromJson

object RTWPacketCallback : PacketCallback {

    override fun handleServerSide(buf: ByteBuf, world: World, player: EntityPlayerMP) {}

    override fun handleClientSide(buf: ByteBuf, world: World, player: EntityClientPlayerMP) {
        when (buf.readInt()) { // Check packet-id's
            0 -> ModMain.proxy.rtwData = fromJson(ByteBufUtils.readUTF8String(buf))
        }
    }
}