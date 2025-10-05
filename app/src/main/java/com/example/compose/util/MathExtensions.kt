package com.example.compose.util

import androidx.compose.ui.geometry.Offset
import dev.romainguy.kotlin.math.Float2
import kotlin.math.pow
import kotlin.math.sqrt

fun Offset.toFloat2() = Float2(x = this.x, y = this.y)

fun Float2.normalize(): Float2 {
    val magnitude = sqrt(x.pow(2) + y.pow(2))
    return Float2(x = x / magnitude, y = y / magnitude)
}

fun Float2.pointTo(position: Float2): Float2 = (position - this).normalize()
