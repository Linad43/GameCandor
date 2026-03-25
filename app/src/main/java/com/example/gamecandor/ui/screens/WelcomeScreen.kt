package com.example.gamecandor.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gamecandor.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    var visibleTitle by remember { mutableStateOf(false) }
    var visibleText by remember { mutableStateOf(false) }
    var visibleNextScreen by remember { mutableStateOf(false) }
    val alphaAnimTitle by animateFloatAsState(
        targetValue = if (visibleTitle) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alphaAnimText by animateFloatAsState(
        targetValue = if (visibleText) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alphaAnimNextScreen by animateFloatAsState(
        targetValue = if (visibleNextScreen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(Unit) {
        delay(1000)
        visibleTitle = true
        delay(2000)
        visibleText = true
        delay(3000)
        visibleNextScreen = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            // весь экран кликабелен
            .clickable {
                navController.navigate(Screens.MAIN.name) {
                    popUpTo(Screens.WELCOME.name) { inclusive = true }
                    launchSingleTop = true
                }
            }
    ) {
        // фон
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "Фон",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // стеклянная карточка с контентом
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .alpha(alphaAnimTitle)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.welcome_text),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(alphaAnimText)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.welcome_hint_text),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(alphaAnimNextScreen)
            )
        }
    }
}