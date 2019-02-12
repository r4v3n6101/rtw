package network

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.network.ByteBufUtils
import cpw.mods.fml.common.network.FMLEventChannel
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint
import cpw.mods.fml.common.network.internal.FMLProxyPacket
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetHandlerPlayServer

/**
 * @author agravaine
 */
class NetworkManager(private val channelName: String, private val callback: PacketCallback) {
    private val channel: FMLEventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName)

    init {
        channel.register(this)
    }

    @SubscribeEvent
    fun onServerPacket(e: ServerCustomPacketEvent) {
        val player = (e.handler as NetHandlerPlayServer).playerEntity
        val buf = e.packet.payload()
        callback.handleServerSide(buf, player.worldObj, player)
    }

    @SubscribeEvent
    fun onClientPacket(event: ClientCustomPacketEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val buf = event.packet.payload()
        callback.handleClientSide(buf, player.worldObj, player)
    }

    fun sendToServer(vararg data: Any) {
        channel.sendToServer(createPacket(*data))
    }

    fun sendToClients(vararg data: Any) {
        channel.sendToAll(createPacket(*data))
    }

    fun sendToPlayer(player: EntityPlayerMP, vararg data: Any) {
        channel.sendTo(createPacket(*data), player)
    }

    fun sendToArea(x: Double, y: Double, z: Double, range: Double, dim: Int, vararg data: Any) {
        channel.sendToAllAround(createPacket(*data), TargetPoint(dim, x, y, z, range))
    }

    fun sendToDimension(id: Int, vararg data: Any) {
        channel.sendToDimension(createPacket(*data), id)
    }

    private fun createPacket(vararg data: Any): FMLProxyPacket {
        return createPacket(Unpooled.buffer(32), *data)
    }

    private fun createPacket(buf: ByteBuf, vararg data: Any): FMLProxyPacket {
        data.forEach {
            when (it) {
                is Boolean -> buf.writeBoolean(it)
                is Byte -> buf.writeByte(it.toInt())
                is Short -> buf.writeShort(it.toInt())
                is Int -> buf.writeInt(it)
                is Float -> buf.writeFloat(it)
                is Double -> buf.writeDouble(it)
                is Long -> buf.writeLong(it)
                is Char -> buf.writeChar(it.toInt())
                is String -> ByteBufUtils.writeUTF8String(buf, it)
                is ItemStack -> ByteBufUtils.writeItemStack(buf, it)
                is NBTTagCompound -> ByteBufUtils.writeTag(buf, it)
            }
        }
        return FMLProxyPacket(buf, channelName)
    }
}
