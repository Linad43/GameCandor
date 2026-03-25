package com.example.gamecandor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gamecandor.R
import com.example.gamecandor.data.LanguageText

@Composable
fun LanguageDialog(
    current: LanguageText,
    onSelect: (LanguageText) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(R.string.choice_language),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                LanguageItem(
                    stringResource(R.string.text_ru),
                    current == LanguageText.RU
                ) {
                    onSelect(LanguageText.RU)
                }
            }
        },
        confirmButton = {}
    )
}