package org.voidsynchron.model

class SimpleArgument(override val type: Type, private val arg: Int): CronArgument() {
    @JvmInline
    value class Parser(private val value: Int): CronArgumentParser<SimpleArgument> {
        override fun parse(type: Type) = SimpleArgument(type, value)
    }
    companion object {
        fun validate(raw: String): Parser? = raw.toIntOrNull()?.let { Parser(it) }
    }
    override fun expand() = listOf(arg)
}
