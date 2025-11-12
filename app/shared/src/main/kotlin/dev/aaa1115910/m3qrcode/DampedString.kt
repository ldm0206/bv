package dev.aaa1115910.m3qrcode

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class DampedString(period: Int, private val dampingRatio: Float) {
    private val dampedNaturalFrequency: Float
    private val stiffness: Float
    private val undampedNaturalFrequency: Float

    init {
        stiffness = ((6.2831855f / period).toDouble().pow(2.0).toFloat()) * 1.0f
        val sqrt = sqrt((stiffness / 1.0f).toDouble()).toFloat()
        undampedNaturalFrequency = sqrt
        dampedNaturalFrequency = sqrt * (sqrt(abs(1 - dampingRatio.pow(2f))))
    }

    fun calculatePosition(i: Int): Float {
        val f = undampedNaturalFrequency * dampingRatio
        val f2 = dampedNaturalFrequency
        val f3 = ((f * (-1.0f)) + 0.0f) / f2
        val d = (f2 * i).toDouble()
        return ((exp(((-f) * i))) * ((f3 * (sin(d).toFloat())) + ((cos(d).toFloat()) * (-1.0f)))) + 1.0f
    }
}
