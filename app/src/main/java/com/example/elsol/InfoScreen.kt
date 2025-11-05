package com.example.elsol

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.tooling.preview.Preview
import com.example.elsol.ui.theme.ElSolTheme

@Composable
fun InfoScreen(modifier: Modifier) {
    Box(modifier.fillMaxSize()) {
        Text("Pantalla 2")
    }
}

@Preview(showBackground = true)
@Composable
fun InfoScreenPreview() {
    ElSolTheme {
        InfoScreen(modifier = Modifier)
    }
}