package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.voidsynchron.model.CronArgument
import org.voidsynchron.model.RangeArgument
import kotlin.test.expect

class RangeArgumentTest {

    @Test
    fun `expand returns the arguments as a list of integers for months`() {
        val arg = RangeArgument(CronArgument.Type.Months, 1, 9)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(1, 2, 3, 4, 5, 6, 7, 8, 9) }
    }

    @Test
    fun `expand returns the arguments as a list of integers for hours`() {
        val arg = RangeArgument(CronArgument.Type.Hours, 7, 11)
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(7, 8, 9, 10, 11) }
    }

    @Test
    fun `validate succeeds with a valid list argument`() {
        val rawListArg = "4-8"
        val parser = RangeArgument.validate(rawListArg)
        assertNotNull(parser)
    }

    @Test
    fun `validate fails with an invalid list argument`() {
        val rawListArg = "4_8"
        val parser = RangeArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid wildcard argument`() {
        val rawListArg = "&-3"
        val parser = RangeArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid extra length argument`() {
        val rawListArg = "1-2-3"
        val parser = RangeArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid string argument`() {
        val rawListArg = "1-z"
        val parser = RangeArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `toString returns the correct representation`() {
        val rawListArg = "4-7"
        val parser = RangeArgument.validate(rawListArg)!!
        assertNotNull(parser)
        val cronArg = parser.parse(CronArgument.Type.Days)
        val result = cronArg.toString()
        expect(result) { "day of month  4 5 6 7" }
    }

    @Test
    fun `parse succeeds with a valid list argument`() {
        val parser = RangeArgument.Parser(3 to 12)
        val listArgument = parser.parse(CronArgument.Type.Minutes)
        expect(listArgument.expand()) {  listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12) }
    }

}
