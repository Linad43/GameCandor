package com.example.gamecandor.ui.components

import android.R
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Card
import com.example.gamecandor.model.Category
import kotlinx.coroutines.delay

//@Composable
//fun CardItem(
//    card: Card,
//    onClick: (Card) -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .size(80.dp, 150.dp)
//            .clickable { onClick(card) },
//        shape = RoundedCornerShape(20.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//        ) {
//            Image(
//                painter = painterResource(card.category.background),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//            Text(
//                text = (card.id - (card.category.ordinal * 22)).toString(),
//                modifier = Modifier
//                    .background(
//                        Color.White.copy(alpha = 0.4f),
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .padding(10.dp)
//            )
//        }
//    }
//}
@Composable
fun CardItem(
    card: Card,
    onOpen: (Card) -> Unit
) {

    var opened by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (opened) 180f else 0f,
        label = "flip"
    )

    val scale by animateFloatAsState(
        targetValue = if (opened) 1.6f else 1f,
        label = "scale"
    )

    val z by animateFloatAsState(
        targetValue = if (opened) 1f else 0f,
        label = "zindex"
    )

    Card(
        modifier = Modifier
            .size(80.dp, 150.dp)
            .zIndex(z)
            .graphicsLayer {
                rotationY = rotation
                scaleX = scale
                scaleY = scale
                cameraDistance = 12 * density
            }
            .clickable {

                if (!opened) {
                    opened = true
                }

            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        if (rotation <= 90f) {

            CardBack(card)

        } else {

            LaunchedEffect(Unit) {
                delay(300)
                onOpen(card)
            }

            CardView(card)
        }
    }
}
@Composable
fun CardBack(card: Card) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(card.category.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = (card.id - (card.category.ordinal * 22)).toString(),
            modifier = Modifier
                .background(
                    Color.White.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp),
            fontSize = 28.sp
        )
    }
}
@Composable
fun CardFront(card: Card) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

//        Image(
//            painter = painterResource(card.category.background),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
    }
}