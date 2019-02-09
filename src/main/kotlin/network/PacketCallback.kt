package network

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import io.netty.buffer.ByteBuf
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.World

interface PacketCallback {
    fun handleServerSide(buf: ByteBuf, world: World, player: EntityPlayerMP)

    @SideOnly(Side.CLIENT)
    fun handleClientSide(buf: ByteBuf, world: World, player: EntityClientPlayerMP)
}