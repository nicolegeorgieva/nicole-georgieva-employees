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
class ParseCsv : FreeSpec({
    "parse Csv" - {
        "parseCsv should return empty list when input is null" {
            val result = parseCsv(null)
            result.shouldBeEmpty()
        }

        "parseCsv should return a list of Employee objects for valid input" {
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

        "parseCsv should handle empty input correctly" {
            val csv = ""
            val result = parseCsv(csv)
            result.shouldBeEmpty()
        }
    }

    "Extract Csv rows" - {
        "extractCsvRows should return a list of rows from the csv string, excluding the header row" {
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

    "Parse Row" - {
        "parseRow should return an Employee object for a valid row of the form: empId,projectId,dateFrom,dateTo" {
            val row = "1,233,2013-11-01,2014-01-05"
            val expectedResult = Employee(
                1, 233, LocalDate.of(2013, Month.NOVEMBER.value, 1),
                LocalDate.of(2014, Month.JANUARY.value, 5)
            )

            val result = parseRow(row)
            result.shouldBe(expectedResult)
        }
    }

    "Parse Date" - {
        "parseDate should return a LocalDate object for a valid date string of the form: yyyy-MM-dd" {
            val date = "2013-11-01"
            val expectedResult = LocalDate.of(2013, Month.NOVEMBER.value, 1)

            val result = parseDate(date)
            result.shouldBe(expectedResult)
        }
    }
})