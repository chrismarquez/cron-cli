package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.voidsynchron.model.CronArgument
import org.voidsynchron.model.SimpleArgument
import kotlin.test.expect

class SimpleArgumentTest {

    @Test
    fun `expand returns the arguments as a list of integers for minutes`() {
        val arg = SimpleArgument(CronArgument.Type.Minutes, 20)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(20) }
    }

    @Test
    fun `expand returns the arguments as a list of integers for days`() {
        val arg = SimpleArgument(CronArgument.Type.Days, 5)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(5) }
    }

    @Test
    fun `validate succeeds with a valid argument`() {
        val rawListArg = "50"
        val parser = SimpleArgument.validate(rawListArg)
        assertNotNull(parser)
    }

    @Test
    fun `validate fails with an invalid list argument`() {
        val rawListArg = "@"
        val parser = SimpleArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `parse succeeds with a valid argument`() {
        val parser = SimpleArgument.Parser(9)
        val listArgument = parser.parse(CronArgument.Type.Hours)
        expect(listArgument.expand()) {  listOf(9) }
    }

}
