package com.kalcreations.minicart

import androidx.compose.runtime.Composable
import com.kalcreations.minicart.ui.theme.MiniCartTheme

@Composable
fun MiniCartApp() {
    MiniCartTheme() {
        NavGraph()
    }
}