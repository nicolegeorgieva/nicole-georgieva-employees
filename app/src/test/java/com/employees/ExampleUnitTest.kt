package com.employees

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : FreeSpec({
    "1 + 1 should be 2" {
        val sum = 1 + 1 shouldBe 2
    }
})