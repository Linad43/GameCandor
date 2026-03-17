package com.example.gamecandor.ui.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun GameExistDialog(
    show: Boolean,
    onRestart: () -> Unit,
    onContinue: () -> Unit,
    onCancel: () -> Unit
) {
    if (!show) return

    Dialog(onDismissRequest = onCancel) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Игра уже существует",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Игра с таким названием уже есть.\nЧто хотите сделать?",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Кнопки
                Column{

                    TextButton(
                        onClick = onRestart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔄 Начать заново")
                    }

                    TextButton(
                        onClick = onContinue,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("▶️ Продолжить")
                    }

                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Отмена")
                    }
                }
            }
        }
    }
}