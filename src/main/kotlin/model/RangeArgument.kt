package org.voidsynchron.model

class RangeArgument(override val type: Type, private val start: Int, private val end: Int): CronArgument() {
    @JvmInline
    value class Parser(private val bounds: Pair<Int, Int>): CronArgumentParser<RangeArgument> {
        override fun parse(type: Type) = RangeArgument(type, bounds.first, bounds.second)
    }
    companion object {
        fun validate(raw: String): Parser? {
            if ('-' !in raw) return null
            val components = raw.split("-")
            if (components.size != 2) return null
            val (first, last) = components
            val start = first.toIntOrNull() ?: return null
            val end = last.toIntOrNull() ?: return null
            return Parser(start to end)
        }
    }
    override fun expand(): List<Int> {
        val range = start .. end
        return range.toList()
    }
}
