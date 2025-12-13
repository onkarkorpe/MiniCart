package com.kalcreations.minicart.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalcreations.minicart.ui.theme.MiniCartTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    // Temporary sample data (UI only)
    val cartItems = listOf(
        CartItemSample.wirelessHeadphone,
        CartItemSample.bluetoothSpeaker,
        CartItemSample.earbuds,
        CartItemSample.phoneCase,
        CartItemSample.powerBank
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        cartItem = item,
                        onIncrease = {},
                        onDecrease = {},
                        onRemove = {}
                    )
                }
            }

            CouponSection(
                isApplyEnabled = true,
                disabledMessage = "Add items worth ₹1000 to apply this coupon",
                onApplyCoupon = {}
            )

            TotalsSection()

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Checkout")
            }
        }
    }
}


@Preview
@Composable
fun CartScreenPreview() {
    MiniCartTheme() {
        CartScreen(
            onBack = { },
            onCheckoutClick = {}
        )
    }
}