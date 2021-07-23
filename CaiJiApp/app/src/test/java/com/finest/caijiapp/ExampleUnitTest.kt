package com.finest.caijiapp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var a = 2
        var b = 2
        var re = a+b
        assertEquals("4", re.toString())
    }
}
