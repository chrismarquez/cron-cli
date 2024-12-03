package org.voidsynchron.model

class PeriodicArgument(override val type: Type, private val period: Int): CronArgument() {
    @JvmInline
    value class Parser(private val period: Int): CronArgumentParser<PeriodicArgument> {
        override fun parse(type: Type) = PeriodicArgument(type, period)
    }
    companion object {
        fun validate(raw: String): Parser? {
            if ('/' !in raw) return null
            val components = raw.split("/")
            if (components.size != 2) return null
            val (first, last) = components
            if (first != "*") return null
            val period = last.toIntOrNull() ?: return null
            return Parser(period)
        }
    }
    override fun expand(): List<Int> {
        val range = type.lowerBound .. type.upperBound step period
        return range.toList()
    }
}
