package com.employees

import com.employees.domain.data.Employee
import com.employees.domain.employeesWithSameProjectId
import com.employees.domain.findEmployeesWithSameProjectId
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

class EmployeesWithSameProjectIdTest : FreeSpec({
    "findEmployeesWithSameProjectId" - {
        "empty list" {
            findEmployeesWithSameProjectId(emptyList()).shouldBe(emptyMap())
        }

        "one employee" {
            findEmployeesWithSameProjectId(
                listOf(
                    Employee(
                        1, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    )
                )
            ).shouldBe(emptyMap())
        }

        "two employees, different project ids" {
            findEmployeesWithSameProjectId(
                listOf(
                    Employee(
                        1, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    ),
                    Employee(
                        2, 2, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    )
                )
            ).shouldBe(emptyMap())
        }

        "two employees, same project ids" {
            findEmployeesWithSameProjectId(
                listOf(
                    Employee(
                        1, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    ),
                    Employee(
                        2, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    )
                )
            ).shouldBe(
                mapOf(
                    1 to listOf(
                        Employee(
                            1, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        ),
                        Employee(
                            2, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        )
                    )
                )
            )
        }

        "two same, one different project id" {
            findEmployeesWithSameProjectId(
                listOf(
                    Employee(
                        1, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    ),
                    Employee(
                        2, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    ),
                    Employee(
                        3, 2, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    )
                )
            ).shouldBe(
                mapOf(
                    1 to listOf(
                        Employee(
                            1, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        ),
                        Employee(
                            2, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        )
                    )
                )
            )
        }

        "three employees, same project ids" {
            findEmployeesWithSameProjectId(
                listOf(
                    Employee(
                        1, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    ),
                    Employee(
                        2, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    ),
                    Employee(
                        3, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    )
                )
            ).shouldBe(
                mapOf(
                    1 to listOf(
                        Employee(
                            1, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        ),
                        Employee(
                            2, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        ),
                        Employee(
                            3, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        )
                    )
                )
            )
        }
    }

    "employeesWithSameProjectId" - {
        "empty file" {
            employeesWithSameProjectId("").shouldBe(emptyMap())
        }

        "one employee file" {
            employeesWithSameProjectId(
                """
                empId,projectId,dateFrom,dateTo
            1,1,2020-04-20,2022-04-20
            """.trimIndent()
            ).shouldBe(emptyMap())
        }

        "two employees, different project ids" {
            employeesWithSameProjectId(
                """
                empId,projectId,dateFrom,dateTo
            1,1,2020-04-20,2022-04-20
            2,2,2020-04-20,2022-04-20
            """.trimIndent()
            ).shouldBe(emptyMap())
        }

        "two employees, same project ids" {
            employeesWithSameProjectId(
                """
                empId,projectId,dateFrom,dateTo
            1,1,2020-04-20,2022-04-20
            2,1,2020-04-20,2022-04-20
            """.trimIndent()
            ).shouldBe(
                mapOf(
                    1 to listOf(
                        Employee(
                            1, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        ),
                        Employee(
                            2, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        )
                    )
                )
            )
        }

        "two same, one different project id" {
            employeesWithSameProjectId(
                """
                empId,projectId,dateFrom,dateTo
            1,1,2020-04-20,2022-04-20
            2,1,2020-04-20,2022-04-20
            3,2,2020-04-20,2022-04-20
            """.trimIndent()
            ).shouldBe(
                mapOf(
                    1 to listOf(
                        Employee(
                            1, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        ),
                        Employee(
                            2, 1, LocalDate.of(2020, Month.APRIL, 20),
                            LocalDate.of(2022, Month.APRIL, 20)
                        )
                    )
                )
            )
        }
    }
})