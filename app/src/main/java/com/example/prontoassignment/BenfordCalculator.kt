package com.example.prontoassignment

class BenfordCalculator {

    @Throws(BenfordCalculationException::class)
    fun conforms(stringInput: String): Boolean {
        val numbers = parseStringInput(stringInput)
        val leadingNumbers = getSignificantLeadingNumbers(numbers)

        val mapOfOccurrencesByNumber = mutableMapOf<Int, Int>()
        leadingNumbers.forEach {
            if (mapOfOccurrencesByNumber.containsKey(it)) {
                mapOfOccurrencesByNumber[it] = mapOfOccurrencesByNumber.getValue(it) + 1
            } else {
                mapOfOccurrencesByNumber[it] = 1
            }
        }

        val mapOfFrequencyByNumber = mutableMapOf<Int, Float>()
        for (i in 1..9) {
            if (mapOfOccurrencesByNumber.containsKey(i)) {
                mapOfFrequencyByNumber[i] = mapOfOccurrencesByNumber.getValue(i) / leadingNumbers.size.toFloat()
            } else {
                mapOfFrequencyByNumber[i] = 0f
            }
        }

        var howMuchOff = 0.0
        for (i in 1..9) {
            val benfordsDistribution = getBenfordsDistribution(i).toFloat()
            val ourValue = mapOfFrequencyByNumber[i] ?: 0.0f

            howMuchOff += if (benfordsDistribution > ourValue) {
                benfordsDistribution - ourValue
            } else {
                ourValue - benfordsDistribution
            }
        }

        return howMuchOff < 0.1
    }

    fun getBenfordsDistribution(number: Int): Double {
        return kotlin.math.log10(1 + (1.0 / number))
    }

    @Throws(IllegalArgumentException::class)
    fun parseStringInput(stringInput: String): List<Float> {
        return stringInput.split(",").map {
            try {
                it.toFloat()
            } catch (e: NumberFormatException) {
                throw BenfordCalculationException(BenfordCalculationErrorType.PARSE_ERROR)
            }
        }
    }

    @Throws(BenfordCalculationException::class)
    fun getSignificantLeadingNumbers(numbers: List<Float>): List<Int> {
        return numbers.map {
            var modifiedNumber = kotlin.math.abs(it)

            if (modifiedNumber == 0f) {
                throw BenfordCalculationException(BenfordCalculationErrorType.NUMBER_ZERO)
            }
            while (modifiedNumber >= 10f) {
                modifiedNumber /= 10.0f
            }
            while (modifiedNumber < 1) {
                modifiedNumber *= 10.0f
            }

            (modifiedNumber / 1).toInt()
        }
    }
}

enum class BenfordCalculationErrorType {
    NUMBER_ZERO,
    PARSE_ERROR
}

class BenfordCalculationException(val errorType: BenfordCalculationErrorType): IllegalArgumentException()