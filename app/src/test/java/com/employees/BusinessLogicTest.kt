package com.employees

import com.employees.domain.calculateOverlappingDuration
import com.employees.domain.data.Employee
import com.employees.domain.longestWorkingTogetherEmployees
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class EmployeeFunctionsTest : FreeSpec({
    "calculateOverlappingDuration should return correct overlapping days" {
        val date1From = LocalDate.of(2021, 1, 1)
        val date1To = LocalDate.of(2021, 1, 10)
        val date2From = LocalDate.of(2021, 1, 5)
        val date2To = LocalDate.of(2021, 1, 15)

        val overlappingDuration = calculateOverlappingDuration(
            date1From, date1To, date2From, date2To
        )
        overlappingDuration shouldBeExactly 6
    }

    "calculateOverlappingDuration should return 0 if no overlapping days" {
        val date1From = LocalDate.of(2021, 1, 1)
        val date1To = LocalDate.of(2021, 1, 10)
        val date2From = LocalDate.of(2021, 1, 11)
        val date2To = LocalDate.of(2021, 1, 15)

        val overlappingDuration = calculateOverlappingDuration(
            date1From, date1To, date2From, date2To
        )
        overlappingDuration shouldBeExactly 0
    }

    "longestWorkingTogetherEmployees should return the correct result" {
        val employees = listOf(
            Employee(
                1, 1, LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 10)
            ),
            Employee(
                2, 1, LocalDate.of(2021, 1, 5),
                LocalDate.of(2021, 1, 15)
            ),
            Employee(
                1, 2, LocalDate.of(2021, 1, 15),
                LocalDate.of(2021, 1, 25)
            ),
            Employee(
                2, 2, LocalDate.of(2021, 1, 15),
                LocalDate.of(2021, 1, 25)
            ),
            Employee(
                3, 2, LocalDate.of(2021, 1, 15),
                LocalDate.of(2021, 1, 25)
            )
        )

        val result = longestWorkingTogetherEmployees(employees)

        result?.employee1Id shouldBe 1
        result?.employee2Id shouldBe 2
        result?.commonProjects shouldBe listOf(1, 2)
        result?.daysWorked shouldBe listOf(6, 11)
    }

    "longestWorkingTogetherEmployees should return null if no common projects" {
        val employees = listOf(
            Employee(
                1, 1, LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 10)
            ),
            Employee(
                2, 2, LocalDate.of(2021, 1, 15),
                LocalDate.of(2021, 1, 25)
            ),
            Employee(
                3, 3, LocalDate.of(2021, 1, 15),
                LocalDate.of(2021, 1, 25)
            )
        )

        val result = longestWorkingTogetherEmployees(employees)
        result.shouldBeNull()
    }
})
