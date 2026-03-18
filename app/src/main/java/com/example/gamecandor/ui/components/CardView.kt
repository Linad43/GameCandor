package com.example.gamecandor.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Card
import kotlin.math.roundToInt

@Composable
fun CardContent(card: Card) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
//            .height(420.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(card.category.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                card.questionIds.forEach { id ->
                    val text = CardRepository.getQuestionText(context, id)
                    QuestionText(
                        text,
                        modifier = Modifier
                            .fillMaxWidth()
//                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun SwipeableCard(
    card: Card,
    onPlayed: () -> Unit,
    onCancel: () -> Unit
) {

    var offsetX by remember { mutableStateOf(0f) }

    val hintAlpha by animateFloatAsState(
        targetValue = (kotlin.math.abs(offsetX) / 300f).coerceIn(0f, 1f),
        label = "hintAlpha"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // подсказка справа
        if (offsetX > 0) {
            Text(
                text = "Сыграть →",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
                modifier = Modifier
                    .zIndex(2f)
                    .align(Alignment.CenterEnd)
                    .padding(52.dp)
                    .alpha(hintAlpha)
            )
        }

        // подсказка слева
        if (offsetX < 0) {
            Text(
                text = "← Вернуть",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
                modifier = Modifier
                    .zIndex(2f)
                    .align(Alignment.CenterStart)
                    .padding(52.dp)
                    .alpha(hintAlpha)
            )
        }
        Card(
            modifier = Modifier
//                .size(220.dp, 360.dp)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            offsetX += dragAmount.x
                        },
                        onDragEnd = {
                            when {
                                offsetX > 300 -> onPlayed()
                                offsetX < -300 -> onCancel()
                                else -> offsetX = 0f
                            }
                        }
                    )
                },
            shape = RoundedCornerShape(20.dp)
        ) {
            CardContent(card)
        }
    }
}