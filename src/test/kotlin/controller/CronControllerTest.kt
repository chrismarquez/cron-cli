package controller

import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.Test
import org.voidsynchron.controller.CronController
import kotlin.test.assertTrue
import kotlin.test.expect

class CronControllerTest {

    @Test
    fun `cron-cli works returns a valid table for a valid cron string`() {
        val cli = CronController()
        val result = cli.test("""
            "*/2 0 1,21 * 1-8" /usr/bin/find 
        """.trimIndent())
        val expectedOutput = """
            minute        0 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54 56 58
            hour          0
            day of month  1 21
            month         1 2 3 4 5 6 7 8 9 10 11 12
            day of week   1 2 3 4 5 6 7 8
            command       /usr/bin/find
        """.trimIndent().trim()
        expect(result.stdout.trim()) { expectedOutput }
        expect(result.statusCode) { 0 }
        assertTrue(result.stderr.isEmpty())
    }

    @Test
    fun `cron-cli returns an error for incomplete cron strings`() {
        val cli = CronController()
        val result = cli.test("""
            "*/2 0 1,21 *" /usr/bin/find 
        """.trimIndent())
        val expectedError = """
            Usage: cron-cli [<options>] <cronargs> <command>...
            
            Error: invalid value for <cronargs>: five values are required
        """.trimIndent().trim()
        expect(result.stderr.trim()) { expectedError }
        assertTrue(result.stdout.isBlank())
        expect(result.statusCode) { 1 }
    }

    @Test
    fun `cron-cli returns an error for invalid cron strings`() {
        val cli = CronController()
        val result = cli.test("""
            "*/2 0 1,21 * not-a-valid-cron-value" docker ps
        """.trimIndent())
        val expectedError = """
            Usage: cron-cli [<options>] <cronargs> <command>...
            
            Error: invalid value for <cronargs>: unsupported value not-a-valid-cron-value for <weekdays>
        """.trimIndent().trim()
        expect(result.stderr.trim()) { expectedError }
        assertTrue(result.stdout.isBlank())
        expect(result.statusCode) { 1 }
    }

}
