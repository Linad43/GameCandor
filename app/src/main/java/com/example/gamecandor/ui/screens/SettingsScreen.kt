package com.example.gamecandor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.TextSize
import com.example.gamecandor.data.toReadable
import com.example.gamecandor.ui.components.LanguageDialog
import com.example.gamecandor.ui.components.SettingsItem
import com.example.gamecandor.ui.components.SettingsSection
import com.example.gamecandor.ui.components.TextSizeDialog
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun SettingsScreen(
    navController: NavHostController,
    onTextSizeChange: (TextSize) -> Unit
) {
    val context = LocalContext.current

    var languageText by remember { mutableStateOf(GameRepository.getLanguageText()) }
    var textSize by remember { mutableStateOf(GameRepository.getSizeText()) }

    var showLanguageDialog by remember { mutableStateOf(false) }
    var showTextSizeDialog by remember { mutableStateOf(false) }

    // 🌍 Диалог языка
    if (showLanguageDialog) {
        LanguageDialog(
            current = languageText,
            onSelect = {
                languageText = it
                GameRepository.setLanguageText(languageText)
                GameRepository.saveSettings(
                    context,
                    languageText,
                    textSize
                )
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }

    // 🔠 Диалог размера текста
    if (showTextSizeDialog) {
        TextSizeDialog(
            current = textSize,
            onSelect = {
                textSize = it
                GameRepository.setTextSize(textSize)
                GameRepository.saveSettings(
                    context,
                    languageText,
                    textSize
                )
                onTextSizeChange(it)
                showTextSizeDialog = false
            },
            onDismiss = { showTextSizeDialog = false }
        )
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.settings),
                showBack = true,
                onBack = { navController.popBackStack() },
                showMenu = false,
                menuItems = listOf()
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    SettingsSection(title = stringResource(R.string.common)) {

                        // 🌍 Язык
                        SettingsItem(
                            title = stringResource(R.string.language),
                            subtitle = languageText.toReadable(context)
                        ) {
                            showLanguageDialog = true
                        }

                        // 🔠 Размер текста
                        SettingsItem(
                            title = stringResource(R.string.size_text),
                            subtitle = textSize.toReadable(context)
                        ) {
                            showTextSizeDialog = true
                        }
                    }
                }
                item {
                    SettingsSection(title = stringResource(R.string.data)) {

                        SettingsItem(
                            title = stringResource(R.string.reset_progress),
                            subtitle = stringResource(R.string.delete_all_cards)
                        ) {
                            // показать диалог подтверждения
                        }
                    }
                }

            }
        }
    )
}