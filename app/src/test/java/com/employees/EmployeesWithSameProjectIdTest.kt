package com.employees

import com.employees.domain.employeesWithSameProjectId
import com.employees.domain.findEmployeesWithSameProjectId
import com.file.Employee
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month

class EmployeesWithSameProjectIdTest : FreeSpec({
    "findEmployeesWithSameProjectId" - {
        "for empty list should return empty map" {
            findEmployeesWithSameProjectId(emptyList()).shouldBe(emptyMap())
        }

        "for list with one employee should return empty map" {
            findEmployeesWithSameProjectId(
                listOf(
                    Employee(
                        1, 1, LocalDate.of(2020, Month.APRIL, 20),
                        LocalDate.of(2022, Month.APRIL, 20)
                    )
                )
            ).shouldBe(emptyMap())
        }

        "for list with two employees with different project ids should return empty map" {
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

        "for list with two employees with same project ids should return map with one entry" {
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

        "for list with two employees with same project ids and one employee with different project id should return map with one entry" {
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

        "for list with three employees with same project ids should return map with one entry" {
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
        "for empty file should return empty map" {
            employeesWithSameProjectId("").shouldBe(emptyMap())
        }

        "for file with one employee should return empty map" {
            employeesWithSameProjectId(
                """
                    empId,projectId,dateFrom,dateTo
                1,1,2020-04-20,2022-04-20
                """.trimIndent()
            ).shouldBe(emptyMap())
        }

        "for file with two employees with different project ids should return empty map" {
            employeesWithSameProjectId(
                """
                    empId,projectId,dateFrom,dateTo
                1,1,2020-04-20,2022-04-20
                2,2,2020-04-20,2022-04-20
                """.trimIndent()
            ).shouldBe(emptyMap())
        }

        "for file with two employees with same project ids should return map with one entry" {
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

        "for file with two employees with same project ids and one employee with different project id" +
                "should return map with one entry" {
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