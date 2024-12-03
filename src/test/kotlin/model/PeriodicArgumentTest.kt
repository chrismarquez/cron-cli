package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.voidsynchron.model.CronArgument
import org.voidsynchron.model.PeriodicArgument
import kotlin.test.expect

class PeriodicArgumentTest {

    @Test
    fun `expand returns the arguments as a list of integers for days`() {
        val arg = PeriodicArgument(CronArgument.Type.Days, 5)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(1, 6, 11, 16, 21, 26) }
    }

    @Test
    fun `expand returns the arguments as a list of integers for weekdays`() {
        val arg = PeriodicArgument(CronArgument.Type.Weekdays, 2)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(1, 3, 5, 7) }
    }

    @Test
    fun `validate succeeds with a valid list argument`() {
        val rawListArg = "*/6"
        val parser = PeriodicArgument.validate(rawListArg)
        assertNotNull(parser)
    }

    @Test
    fun `validate fails with an invalid list argument`() {
        val rawListArg = "*4"
        val parser = PeriodicArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid wildcard argument`() {
        val rawListArg = "&/4"
        val parser = PeriodicArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid extra length argument`() {
        val rawListArg = "&/2/4"
        val parser = PeriodicArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid string argument`() {
        val rawListArg = "*/x"
        val parser = PeriodicArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `parse succeeds with a valid list argument`() {
        val parser = PeriodicArgument.Parser(9)
        val listArgument = parser.parse(CronArgument.Type.Days)
        expect(listArgument.expand()) {  listOf(1, 10, 19, 28) }
    }

}
