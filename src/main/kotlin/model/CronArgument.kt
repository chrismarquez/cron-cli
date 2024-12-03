package org.voidsynchron.model

sealed class CronArgument {

    enum class Type(
        val lowerBound: Int,
        val upperBound: Int,
        val argName: String,
    ) {
        Minutes(0, 59, "minute"),
        Hours(0, 23, "hour"),
        Days(1, 30, "day of month"),
        Weekdays(1, 7, "day of week"),
        Months(1, 12, "month"),
    }

    protected abstract val type: Type
    abstract fun expand(): List<Int>
    override fun toString(): String {
        val values = expand()
        val nameColumn = type.argName.padEnd(14, ' ')
        val valueColumn = values.joinToString(separator = " ")
        return nameColumn + valueColumn
    }
}

interface CronArgumentParser<T: CronArgument> {
    fun parse(type: CronArgument.Type): T
}
