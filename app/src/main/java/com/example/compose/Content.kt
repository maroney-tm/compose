package com.example.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme
import dev.romainguy.kotlin.math.Float2
import kotlinx.coroutines.delay

const val FRAME_TIME_MS = 10L

@Composable
fun Content(
    modifier: Modifier = Modifier
) {
    var counter by remember { mutableIntStateOf(0) }
    val list = remember { mutableListOf<EntityModel>() }

    LaunchedEffect(Unit) {
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())
        list.add(EntityModel.rand())

        while (true) {
            delay(FRAME_TIME_MS)
            counter++
        }
    }
    Text(
        text = "Elapsed time: ${counter * FRAME_TIME_MS}ms",
        modifier = modifier.padding(8.dp)
    )

    var touchPosition by remember { mutableStateOf<Offset?>(null) }

    Column(modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 1.dp, color = Color.Green)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        touchPosition = down.position
                        var change = awaitTouchSlopOrCancellation(down.id) { change, _ ->
                            change.consume()
                            touchPosition = change.position
                        }
                        while (change != null && change.pressed) {
                            change = awaitDragOrCancellation(change.id)
                            if (change != null && change.pressed) {
                                change.consume()
                                touchPosition = change.position
                            }
                        }
                        touchPosition = null
                    }
                }
        ) {
            list.forEach {
                it.applyForce(Float2(0f, 10f))
                it.draw(this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    ComposeTheme {
        Content()
    }
}