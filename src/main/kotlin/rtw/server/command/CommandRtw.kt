package rtw.server.command

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import rtw.common.ModMain
import rtw.common.util.MOON_PHASES_NAMES
import rtw.common.util.getDaysSinceNewMoon
import rtw.common.util.getMoonPhaseNumber
import rtw.common.util.getSolarHourAngle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommandRtw : CommandBase() {

    override fun getCommandName() = "rtw"

    override fun getRequiredPermissionLevel() = 2

    override fun getCommandUsage(sender: ICommandSender) = "return date & time"

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        val rtwData = ModMain.proxy.rtwData
        val ldt = LocalDateTime.now(rtwData.zoneOffset)
        val daysSinceNewMoon = getDaysSinceNewMoon(ldt.toLocalDate())
        val moonPhase = getMoonPhaseNumber(daysSinceNewMoon)

        func_152373_a(sender, this, ldt.format(DateTimeFormatter.ISO_DATE_TIME))
        func_152373_a(
                sender, this,
                "Solar hour angle (deg): ${getSolarHourAngle(rtwData.longitude.toDouble())}"
        )
        func_152373_a(sender, this, "Moon phase: ${MOON_PHASES_NAMES[moonPhase]}")
        func_152373_a(sender, this, rtwData.toString())
    }
}