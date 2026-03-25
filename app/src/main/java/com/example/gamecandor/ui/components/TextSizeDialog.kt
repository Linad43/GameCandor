package com.example.gamecandor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gamecandor.R
import com.example.gamecandor.data.TextSize

@Composable
fun TextSizeDialog(
    current: TextSize,
    onSelect: (TextSize) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.size_text)) },
        text = {
            Column {
                TextSizeItem(
                    stringResource(R.string.small),
                    current == TextSize.SMALL
                ) {
                    onSelect(TextSize.SMALL)
                }
                TextSizeItem(
                    stringResource(R.string.medium),
                    current == TextSize.MEDIUM
                ) {
                    onSelect(TextSize.MEDIUM)
                }
                TextSizeItem(
                    stringResource(R.string.large),
                    current == TextSize.LARGE
                ) {
                    onSelect(TextSize.LARGE)
                }
            }
        },
        confirmButton = {}
    )
}