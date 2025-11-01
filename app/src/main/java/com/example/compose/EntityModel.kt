package com.example.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.compose.util.pointTo
import dev.romainguy.kotlin.math.Float2
import kotlin.random.Random

private const val BOUNCE = 0.9f
private const val FRICTION = 0.99f

class EntityModel(
    val color: Color,
    val radius: Float,
    private val mass: Float = 1f,
    private var position: Float2 = Float2(x = radius, y = radius),
    private var velocity: Float2 = Float2(x = 0f, y = 0f),
    private var acceleration: Float2 = Float2(x = 0f, y = 0f),
) : BaseEntityViewModel() {

    companion object {
        fun rand(): EntityModel {
            val radius = (5..100).nextFloat()
            return EntityModel(
                color = getRandomOpaqueColor(),
                radius = radius,
                mass = radius,
                velocity = Float2(
                    x = (1..20).nextFloat(),
                    y = (1..20).nextFloat()
                )
            )
        }
    }

    fun applyForce(force: Float2) {
        acceleration += (force / mass)
    }

    fun attractTo(position: Float2) {
        val dir = this.position.pointTo(position)
        applyForce(dir.times(20f))
    }

    override fun onDraw(scope: DrawScope, delta: Float) = with(scope) {
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
            velocity.y *= BOUNCE // make collisions with ground inelastic
        }
        if (position.y - radius < 0) {
            position = position.copy(y = radius)
            velocity *= Float2(1f, -1f)
        }

        velocity += acceleration
        position += velocity.times(delta)

        acceleration = Float2()
        velocity = velocity.times(FRICTION)

        drawCircle(
            color = color,
            radius = radius,
            center = Offset(position.x, position.y)
        )
    }
}

private fun ClosedRange<Int>.nextFloat(): Float =
    Random.nextFloat() * (endInclusive - start) + start

private fun getRandomOpaqueColor() = Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
)