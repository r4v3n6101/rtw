package rtw.commands

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.command.WrongUsageException
import rtw.globalTimeZone
import java.time.OffsetTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CommandRtime : CommandBase() {

    override fun getCommandName() = "rtime"

    override fun getRequiredPermissionLevel() = 2

    override fun getCommandUsage(sender: ICommandSender) = "commands.rtime"

    override fun processCommand(sender: ICommandSender, args: Array<String>) = when {
        args.isEmpty() -> func_152373_a(
                sender, this,
                OffsetTime.now(globalTimeZone).format(DateTimeFormatter.ISO_OFFSET_TIME)
        )
        args.size == 1 -> globalTimeZone = ZoneId.of(args[0])
        else -> throw WrongUsageException("commands.rtime.usage")
    }

    override fun addTabCompletionOptions(sender: ICommandSender, words: Array<String>) =
            if (words.isEmpty())
                ZoneId.getAvailableZoneIds().toList()
            else
                ZoneId.getAvailableZoneIds().filter { it.startsWith(words[0]) }
}