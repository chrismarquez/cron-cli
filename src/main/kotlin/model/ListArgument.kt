package org.voidsynchron.model

class ListArgument(override val type: Type, private val args: List<Int>): CronArgument() {
    @JvmInline
    value class Parser(private val components: List<Int>): CronArgumentParser<ListArgument> {
        override fun parse(type: Type) = ListArgument(type, components)
    }
    companion object {
        fun validate(raw: String): Parser? {
            if (',' !in raw) return null
            val components = raw.split(",").map(String::trim)
            val intComponents = components.mapNotNull { it.toIntOrNull() }
            return if (intComponents.size == components.size) Parser(intComponents) else null
        }
    }
    override fun expand(): List<Int> = args
}
