package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.voidsynchron.model.CronArgument
import org.voidsynchron.model.WildcardArgument
import kotlin.test.expect

class WildcardArgumentTest {

    @Test
    fun `expand returns the arguments as a list of integers for minutes`() {
        val arg = WildcardArgument(CronArgument.Type.Minutes)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { generateSequence(0) { it + 1 }.take(60).toList() }
    }

    @Test
    fun `expand returns the arguments as a list of integers for days`() {
        val arg = WildcardArgument(CronArgument.Type.Days)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { generateSequence(1) { it + 1 }.take(30).toList() }
    }

    @Test
    fun `validate succeeds with a valid argument`() {
        val rawListArg = "*"
        val parser = WildcardArgument.validate(rawListArg)
        assertNotNull(parser)
    }

    @Test
    fun `validate fails with an invalid list argument`() {
        val rawListArg = "anything-else"
        val parser = WildcardArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `parse succeeds with a valid list argument`() {
        val parser = WildcardArgument.Parser
        val listArgument = parser.parse(CronArgument.Type.Weekdays)
        expect(listArgument.expand()) {  generateSequence(1) { it + 1 }.take(7).toList() }
    }

}
