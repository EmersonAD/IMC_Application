package com.souzaemerson.imcapplication.repository

import com.souzaemerson.imcapplication.data.CalculateIMC

class ImcRepositoryImpl(private val boConfig: CalculateIMC): ImcRepository {
    override fun calculateImc(height: Double, weight: Double): String =
        boConfig.calculateImc(height, weight)
}