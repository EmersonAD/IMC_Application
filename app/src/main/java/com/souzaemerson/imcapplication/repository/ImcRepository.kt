package com.souzaemerson.imcapplication.repository

interface ImcRepository {

    fun calculateImc(height: Double, weight: Double): String
}