package com.example.gamecandor.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.data.HtmlFromAssetsScreen
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RulesScreen(navController: NavHostController) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.rules),
                showBack = true,
                onBack = { navController.popBackStack() },
                showMenu = false,
                menuItems = listOf()
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier.padding(
                    padding
                )
            ) {
                HtmlFromAssetsScreen()
            }
        }
    )
}
