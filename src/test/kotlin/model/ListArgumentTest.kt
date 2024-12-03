package model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.voidsynchron.model.CronArgument
import org.voidsynchron.model.ListArgument
import kotlin.test.expect

class ListArgumentTest {

    @Test
    fun `expand returns the arguments as a list of integers`() {
        val arg = ListArgument(CronArgument.Type.Days, listOf(1, 2, 3))
        val expandedArgs = arg.expand()
        expect(expandedArgs) { listOf(1, 2, 3) }
    }

    @Test
    fun `validate succeeds with a valid list argument`() {
        val rawListArg = "1, 2, 3"
        val parser = ListArgument.validate(rawListArg)
        assertNotNull(parser)
    }

    @Test
    fun `validate fails with an invalid list argument`() {
        val rawListArg = "1-2"
        val parser = ListArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `validate fails with an invalid string list`() {
        val rawListArg = "1,x"
        val parser = ListArgument.validate(rawListArg)
        assertNull(parser)
    }

    @Test
    fun `parse succeeds with a valid list argument`() {
        val parser = ListArgument.Parser(listOf(5, 7, 21))
        val listArgument = parser.parse(CronArgument.Type.Days)
        expect(listArgument.expand()) {  listOf(5, 7, 21) }
    }

}
