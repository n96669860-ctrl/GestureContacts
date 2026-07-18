package com.gesturecontacts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.gesturecontacts.gesture.GesturePoint
import com.gesturecontacts.gesture.GestureRecognizer

@Composable
fun GestureCanvas(
    modifier: Modifier = Modifier,
    onGestureRecognized: (Char?) -> Unit
) {
    val points = remember { mutableStateOf<List<GesturePoint>>(emptyList()) }
    val recognizer = remember { GestureRecognizer() }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        points.value = listOf(GesturePoint(offset.x, offset.y))
                    },
                    onDrag = { change, _ ->
                        val newPoint = GesturePoint(change.position.x, change.position.y)
                        points.value = points.value + newPoint
                    },
                    onDragEnd = {
                        // Reconhecer o gesto
                        val recognizedChar = recognizer.recognize(points.value)
                        onGestureRecognized(recognizedChar)
                        
                        // Limpar após um delay
                        points.value = emptyList()
                    }
                )
            }
            .drawWithCache {
                onDrawBehind {
                    // Desenhar os pontos do gesto
                    if (points.value.isNotEmpty()) {
                        for (i in 1 until points.value.size) {
                            val from = points.value[i - 1]
                            val to = points.value[i]
                            
                            drawLine(
                                color = Color.Blue,
                                start = Offset(from.x, from.y),
                                end = Offset(to.x, to.y),
                                strokeWidth = 8f
                            )
                        }
                        
                        // Desenhar os pontos
                        points.value.forEach { point ->
                            drawCircle(
                                color = Color.Blue,
                                radius = 4f,
                                center = Offset(point.x, point.y)
                            )
                        }
                    }
                }
            }
    )
}
