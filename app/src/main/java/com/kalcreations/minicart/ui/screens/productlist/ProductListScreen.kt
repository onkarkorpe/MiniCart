package com.kalcreations.minicart.ui.screens.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalcreations.minicart.ui.components.CartFab
import com.kalcreations.minicart.ui.theme.MiniCartTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onCartClick: () -> Unit,
    viewModel: ProductListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val products = viewModel.products   // state list

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    CartFab(
                        count = viewModel.cartCount,
                        onClick = onCartClick
                    )
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onAddClick = { viewModel.addToCart(product) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductScreenPreview() {
    MiniCartTheme {
        ProductListScreen(
            onCartClick = { }
        )
    }
}