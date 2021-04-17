package com.example.prontoassignment

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BenfordCalculatorTest {

    private lateinit var benfordCalculator: BenfordCalculator

    @Before
    fun setup() {
        benfordCalculator = BenfordCalculator()
    }

    /**
     * parseStringInput
     */

    @Test
    fun `parseStringInput - basic comma separated - works`() {

        val numberList = benfordCalculator.parseStringInput("1,2,3,4.4")

        assertEquals(1f, numberList[0])
        assertEquals(2f, numberList[1])
        assertEquals(3f, numberList[2])
        assertEquals(4.4f, numberList[3])
    }

    @Test
    fun `parseStringInput - one number - works`() {

        val numberList = benfordCalculator.parseStringInput("1")

        assertEquals(1f, numberList[0])
    }

    @Test
    fun `parseStringInput - characters - throws exception`() {
        var exceptionThrown = false
        try {
            benfordCalculator.parseStringInput("1,b,3,4.4")
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `parseStringInput - only commas - throws exception`() {
        var exceptionThrown = false
        try {
            benfordCalculator.parseStringInput(",,,")
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `parseStringInput - empty string - throws exception`() {
        var exceptionThrown = false
        try {
            benfordCalculator.parseStringInput("")
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }
        assertTrue(exceptionThrown)
    }

    /**
     * getSignificantLeadingNumbers
     */

    @Test
    fun `getSignificantLeadingNumbers - Normal cases - work`() {
        val leadingNumbers = benfordCalculator.getSignificantLeadingNumbers(listOf(1f, 200f, 0.003f, 4001f, 0.0052f))

        assertEquals(1, leadingNumbers[0])
        assertEquals(2, leadingNumbers[1])
        assertEquals(3, leadingNumbers[2])
        assertEquals(4, leadingNumbers[3])
        assertEquals(5, leadingNumbers[4])
    }

    @Test
    fun `getSignificantLeadingNumbers - negative numbers - work`() {
        val leadingNumbers = benfordCalculator.getSignificantLeadingNumbers(listOf(-1f, -200f, -0.4f))

        assertEquals(1, leadingNumbers[0])
        assertEquals(2, leadingNumbers[1])
        assertEquals(4, leadingNumbers[2])
    }

    @Test
    fun `getSignificantLeadingNumbers - 0 - throws exception`() {
        var exceptionThrown = false
        try {
            benfordCalculator.getSignificantLeadingNumbers(listOf(0f))
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }
        assertTrue(exceptionThrown)
    }

    /**
     * getBenfordsDistribution
     */

    @Test
    fun `getBenfordsDistribution - all numbers - work`() {
        assertEquals(0.301, benfordCalculator.getBenfordsDistribution(1), .001)
        assertEquals(0.176, benfordCalculator.getBenfordsDistribution(2), .001)
        assertEquals(0.125, benfordCalculator.getBenfordsDistribution(3), .001)
        assertEquals(0.097, benfordCalculator.getBenfordsDistribution(4), .001)
        assertEquals(0.079, benfordCalculator.getBenfordsDistribution(5), .001)
        assertEquals(0.067, benfordCalculator.getBenfordsDistribution(6), .001)
        assertEquals(0.058, benfordCalculator.getBenfordsDistribution(7), .001)
        assertEquals(0.051, benfordCalculator.getBenfordsDistribution(8), .001)
        assertEquals(0.046, benfordCalculator.getBenfordsDistribution(9), .001)
    }

    /**
     * conforms
     */

    @Test
    fun `conforms - based on percentage use that number of numbers - true`() {

        assertTrue(benfordCalculator.conforms("""
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                    3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                    4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                    5, 5, 5, 5, 5, 5, 5, 5,
                    6, 6, 6, 6, 6, 6, 6,
                    7, 7, 7, 7, 7, 7,
                    8, 8, 8, 8, 8,
                    9, 9, 9, 9, 9
                    """))
    }

    @Test
    fun `conforms - small set - false`() {
        assertFalse(benfordCalculator.conforms("1, 2, 3, 4, 5, 6"))
    }

}