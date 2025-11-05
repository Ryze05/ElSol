package com.example.elsol

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.tooling.preview.Preview
import com.example.elsol.ui.theme.ElSolTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InfoScreen(modifier: Modifier) {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showDatePicker by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf<Long?>(null) }

    Box(modifier.fillMaxSize()) {
        Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                loading = true
                scope.launch {
                    loadProgress { currentProgress = it }
                    loading = false
                }
            }, enabled = !loading,
                colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF712ec8),
                contentColor = Color.White)
            ) {
                Text("Download more info")
            }

            if (loading) {
                CircularProgressIndicator(
                    progress = { currentProgress },
                    //modifier = Modifier.fillMaxWidth(),
                )
            }

            Button(
                onClick = { showDatePicker = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF712ec8),
                    contentColor = Color.White)
            ) {
                Text("Visit Planetarium. Select date")
            }

            if (showDatePicker) {
                DatePickerModal(
                    onDateSelected = {
                        date = it
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }

    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true)
@Composable
fun InfoScreenPreview() {
    ElSolTheme {
        InfoScreen(modifier = Modifier)
    }
}