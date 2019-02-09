package rtw.server.commands

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import rtw.server.utils.rtwDataRetriever
import java.time.OffsetTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CommandRtime : CommandBase() {

    override fun getCommandName() = "rtime"

    override fun getRequiredPermissionLevel() = 2

    override fun getCommandUsage(sender: ICommandSender) = "return time"

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        func_152373_a(
                sender, this,
                OffsetTime
                        .now(ZoneId.of(rtwDataRetriever.retrieve().zoneId))
                        .format(DateTimeFormatter.ISO_OFFSET_TIME)
        )
    }
}