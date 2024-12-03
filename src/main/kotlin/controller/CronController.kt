package org.voidsynchron.controller

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.*
import org.voidsynchron.model.*

typealias RawCronArguments = List<Pair<CronArgument.Type, String>>

class CronController: CliktCommand(name = "cron-cli") {

    private val argList = listOf(
        CronArgument.Type.Minutes,
        CronArgument.Type.Hours,
        CronArgument.Type.Days,
        CronArgument.Type.Months,
        CronArgument.Type.Weekdays,
    )

    private val cronArgs by argument()
        .convert { it.parseList(argument) }
        .convert { argList.zip(it) }
        .convert { it.parse(argument) }

    private val command: List<String> by argument().multiple(required = true)

    override fun run() {
        cronArgs.forEach(::echo)
        echo("command".padEnd(14, ' ') + command.joinToString(" "))
    }

    private fun String.parseList(argument: Argument): List<String> {
        return split(" ").apply {
            if (size != 5) throw BadParameterValue("five values are required", argument)
        }
    }

    private fun RawCronArguments.parse(argument: Argument): List<CronArgument> {
        val list: Set<(String) -> CronArgumentParser<*>?> = setOf(
            SimpleArgument::validate,
            WildcardArgument::validate,
            ListArgument::validate,
            PeriodicArgument::validate,
            RangeArgument::validate,
        )
        return map { (type, rawArg) ->
            list.forEach { validator ->
                val parser = validator(rawArg) ?: return@forEach
                return@map parser.parse(type)
            }
            throw BadParameterValue("unsupported value $rawArg for <${type.toString().lowercase()}>", argument)
        }
    }

}
