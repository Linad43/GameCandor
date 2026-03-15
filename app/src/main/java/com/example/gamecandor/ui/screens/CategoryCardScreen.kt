package com.example.gamecandor.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Category
import com.example.gamecandor.ui.components.CardItem
import com.example.gamecandor.model.Card
import com.example.gamecandor.ui.components.CardView
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun CategoryCardsScreen(category: Category) {
    val context = LocalContext.current
    var selectedCard by remember {
        mutableStateOf<Card?>(null)
    }

    val cards = CardRepository.getCardsByCategory(context, category)

    if (selectedCard == null) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cards) { card ->
                CardItem(
                    card = card,
                    onOpen = { clickedCard ->
                        selectedCard = clickedCard
                    }
                )
            }
        }

    } else {
//        CardView(selectedCard!!)
        CardView(
            card = selectedCard!!,
            onPlayed = {
                CardRepository.markCardPlayed(context, selectedCard!!)
                selectedCard = null
            },
            onCancel = {
                selectedCard = null

            }
        )
    }
}
//@Composable
//fun CategoryCardsScreen(category: Category) {
//
//    val context = LocalContext.current
//
//    var selectedCard by remember {
//        mutableStateOf<Card?>(null)
//    }
//
//    val cards =
//        CardRepository
//            .getCardsByCategory(context, category)
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(15.dp)
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(4),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(cards) { card ->
//                CardItem(
//                    card = card,
//                    onClick = {
//                        selectedCard = it
//                    }
//                )
//            }
//        }
//        selectedCard?.let {
////            OpenedCard(
////                card = it,
////                onDismiss = {
////                    CardRepository.markCardPlayed(context, it)
////                    selectedCard = null
////                }
////            )
//            OpenedCard(
//                card = selectedCard!!,
//                onPlayed = {
//                    CardRepository
//                        .markCardPlayed(context, selectedCard!!)
//                    selectedCard = null
//                },
//                onCancel = {
//                    selectedCard = null
//                }
//            )
//
//        }
//    }
//}
//
//@Composable
//fun OpenedCard(
//    card: Card,
//    onDismiss: () -> Unit
//) {
//
//    var offsetX by remember { mutableStateOf(0f) }
//    var offsetY by remember { mutableStateOf(0f) }
//
//    val scale by animateFloatAsState(
//        targetValue = 1.8f,
//        label = ""
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            modifier = Modifier
//                .size(120.dp, 200.dp)
//                .scale(scale)
//                .offset {
//                    IntOffset(
//                        offsetX.roundToInt(),
//                        offsetY.roundToInt()
//                    )
//                }
//                .pointerInput(Unit) {
//                    detectDragGestures(
//                        onDrag = { change, drag ->
//                            offsetX += drag.x
//                            offsetY += drag.y
//                        },
//                        onDragEnd = {
//                            if (
//                                abs(offsetX) > 200 ||
//                                abs(offsetY) > 200
//                            ) {
//                                onDismiss()
//                            } else {
//                                offsetX = 0f
//                                offsetY = 0f
//                            }
//                        }
//                    )
//                },
//            shape = RoundedCornerShape(20.dp)
//        ) {
//            CardView(card)
//        }
//
//    }
//}
//@Composable
//fun OpenedCard(
//    card: Card,
//    onPlayed: () -> Unit,
//    onCancel: () -> Unit
//) {
//
//    var flipped by remember { mutableStateOf(false) }
//
//    var offsetX by remember { mutableStateOf(0f) }
//    var offsetY by remember { mutableStateOf(0f) }
//
//    val scale by animateFloatAsState(
//        targetValue = if (flipped) 2f else 1f,
//        label = "scale"
//    )
//
//    val rotation by animateFloatAsState(
//        targetValue = if (flipped) 180f else 0f,
//        label = "flip"
//    )
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            modifier = Modifier
//                .size(140.dp, 240.dp)
//                .scale(scale)
//                .offset {
//                    IntOffset(
//                        offsetX.roundToInt(),
//                        offsetY.roundToInt()
//                    )
//                }
//                .graphicsLayer {
//                    rotationY = rotation
//                    cameraDistance = 12 * density
//                }
//                .pointerInput(Unit) {
//                    detectDragGestures(
//                        onDrag = { _, drag ->
//                            offsetX += drag.x
//                            offsetY += drag.y
//                        },
//                        onDragEnd = {
//                            when {
//                                offsetX > 300 -> {
//                                    onPlayed()
//                                }
//                                offsetX < -300 -> {
//                                    onCancel()
//                                }
//                                else -> {
//                                    offsetX = 0f
//                                    offsetY = 0f
//                                }
//                            }
//                        }
//                    )
//                }
//                .clickable {
//                    if (!flipped) {
//                        flipped = true
//                    }
//                },
//            shape = RoundedCornerShape(20.dp)
//        ) {
//            if (rotation <= 90f) {
//                CardBack(card)
//            } else {
//                CardView(card)
//            }
//
//        }
//
//    }
//}
//
//@Composable
//fun CardBack(card: Card) {
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//
//        Image(
//            painter = painterResource(card.category.background),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//
//        Text(
//            text = (card.id - (card.category.ordinal * 22)).toString(),
//            modifier = Modifier
//                .background(
//                    Color.White.copy(alpha = 0.4f),
//                    shape = RoundedCornerShape(12.dp)
//                )
//                .padding(10.dp)
//        )
//    }
//}