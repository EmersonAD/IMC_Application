package com.souzaemerson.imcapplication.data

import com.souzaemerson.imcapplication.data.util.*

class CalculateIMC {

    fun calculateImc(height: Double, weight: Double): String {
        val heightCmToMeter: Double = (height / 100.0)
        val heightCalculated = heightCmToMeter * heightCmToMeter
        val imc = weight.div(heightCalculated)

        return when {
            imc >= 0 && imc < 18.5 -> UNDER_WEIGHT_BODY_TYPE
            imc >= 18.5 && imc < 25.0 -> NORMAL_BODY_TYPE
            imc >= 25.0 && imc < 30 -> OVER_WEIGHT_BODY_TYPE
            imc >= 30 && imc < 35 -> OBESITY_ONE_BODY_TYPE
            imc >= 35 && imc < 40 -> OBESITY_TWO_BODY_TYPE
            imc >= 40 -> OBESITY_THREE_BODY_TYPE
            else -> ERROR_TO_CALCULATE_BODY_TYPE
        }
    }
}