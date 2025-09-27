package com.example.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.lifecycle.ViewModel
import dev.romainguy.kotlin.math.Float2
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.TimeSource

class EntityModel(
    val color: Color,
    val radius: Float,
    private var position: Float2 = Float2(radius, radius),
    private var velocity: Float2 = Float2(0f, 0f),
) : ViewModel() {

    companion object {
        fun rand() = EntityModel(
            color = getRandomOpaqueColor(),
            radius = (5..100).nextFloat(),
            velocity = Float2(
                x = (1..20).nextFloat(),
                y = (1..20).nextFloat()
            )
        )
    }

    var lastMark: TimeSource.Monotonic.ValueTimeMark? = null


    fun draw(scope: DrawScope) {
        val delta = lastMark?.let { TimeSource.Monotonic.markNow().minus(it) } ?: Duration.Companion.ZERO

        with(scope) {
            if (position.x + radius > size.width) {
                position = position.copy(x = size.width - radius)
                velocity *= Float2(-1f, 1f)
            }
            if (position.x - radius < 0) {
                position = position.copy(x = radius)
                velocity *= Float2(-1f, 1f)
            }
            if (position.y + radius > size.height) {
                position = position.copy(y = size.height - radius)
                velocity *= Float2(1f, -1f)
            }
            if (position.y - radius < 0) {
                position = position.copy(y = radius)
                velocity *= Float2(1f, -1f)
            }

            position += velocity.times(delta.inWholeMicroseconds.toFloat() / 25_000)

            drawCircle(
                color = color,
                radius = radius,
                center = Offset(position.x, position.y)
            )
        }
        lastMark = TimeSource.Monotonic.markNow()
    }
}

private fun ClosedRange<Int>.nextFloat(): Float =
    Random.nextFloat() * (endInclusive - start) + start

private fun getRandomOpaqueColor() = Color(
    red = Random.nextInt(256), // 0-255
    green = Random.nextInt(256), // 0-255
    blue = Random.nextInt(256)  // 0-255
)