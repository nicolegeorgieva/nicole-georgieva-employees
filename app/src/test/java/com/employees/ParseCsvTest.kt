package com.employees

import com.employees.domain.extractCsvRows
import com.employees.domain.parseCsv
import com.employees.domain.parseDate
import com.employees.domain.parseRow
import com.file.Employee
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ParseCsvTest : FreeSpec({
    "parseCsv" - {
        "null input" {
            val result = parseCsv(null)
            result.shouldBeEmpty()
        }

        "valid input" {
            val csv = """
        empId,projectId,dateFrom,dateTo
        1,233,2013-11-01,2014-01-05
        2,345,2013-11-01,2014-01-05
    """.trimIndent()

            val expectedResult = listOf(
                Employee(
                    1, 233, LocalDate.of(2013, Month.NOVEMBER.value, 1),
                    LocalDate.of(2014, Month.JANUARY.value, 5)
                ),
                Employee(
                    2, 345, LocalDate.of(2013, Month.NOVEMBER.value, 1),
                    LocalDate.of(2014, Month.JANUARY.value, 5)
                )
            )

            val result = parseCsv(csv)
            result.shouldContainExactly(expectedResult)
        }

        "empty input" {
            val csv = ""
            val result = parseCsv(csv)
            result.shouldBeEmpty()
        }
    }

    "extractCsvRows" - {
        "exclude header row" {
            val csv = """
        empId,projectId,dateFrom,dateTo
        1,233,2013-11-01,2014-01-05
        2,345,2013-11-01,2014-01-05
    """.trimIndent()

            val expectedResult = listOf(
                "1,233,2013-11-01,2014-01-05",
                "2,345,2013-11-01,2014-01-05"
            )

            val result = extractCsvRows(csv)
            result.shouldContainExactly(expectedResult)
        }
    }

    "parseRow" - {
        "valid row" {
            val row = "1,233,2013-11-01,2014-01-05"
            val expectedResult = Employee(
                1, 233, LocalDate.of(2013, Month.NOVEMBER.value, 1),
                LocalDate.of(2014, Month.JANUARY.value, 5)
            )

            val result = parseRow(row)
            result.shouldBe(expectedResult)
        }
    }

    "parseDate" - {
        "valid date string" {
            val date = "2013-11-01"
            val expectedResult = LocalDate.of(2013, Month.NOVEMBER.value, 1)

            val result = parseDate(date)
            result.shouldBe(expectedResult)
        }
    }
})