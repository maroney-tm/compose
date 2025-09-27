package com.example.compose

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.lifecycle.ViewModel
import kotlin.time.Duration
import kotlin.time.TimeSource

abstract class BaseEntityViewModel() : ViewModel() {

    private var lastMark: TimeSource.Monotonic.ValueTimeMark? = null

    protected abstract fun onDraw(scope: DrawScope, delta: Float)

    fun draw(scope: DrawScope) {
        val deltaDuration = lastMark?.let { TimeSource.Monotonic.markNow().minus(it) } ?: Duration.ZERO
        val delta = deltaDuration.inWholeMicroseconds.toFloat() / 25_000
        onDraw(scope, delta)
        lastMark = TimeSource.Monotonic.markNow()
    }
}