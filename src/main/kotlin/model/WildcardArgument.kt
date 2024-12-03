package org.voidsynchron.model

class WildcardArgument(override val type: Type): CronArgument() {
    object Parser: CronArgumentParser<WildcardArgument> {
        override fun parse(type: Type) = WildcardArgument(type)
    }
    companion object {
        fun validate(raw: String): Parser? = if (raw == "*") Parser else null
    }
    override fun expand(): List<Int> {
        val range = type.lowerBound .. type.upperBound
        return range.toList()
    }
}
