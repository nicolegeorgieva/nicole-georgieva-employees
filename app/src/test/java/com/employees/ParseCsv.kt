package com.employees

import com.employees.domain.parseCsv
import com.file.Employee
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import java.time.LocalDate
import java.time.Month

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ParseCsv : FreeSpec({
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
})