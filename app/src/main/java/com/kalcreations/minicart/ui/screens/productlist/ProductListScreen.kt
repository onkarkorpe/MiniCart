package com.kalcreations.minicart.ui.screens.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kalcreations.minicart.data.model.Product
import com.kalcreations.minicart.ui.components.CartFab
import com.kalcreations.minicart.ui.theme.MiniCartTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productViewModel: ProductListViewModel,
    cartCount: Int,
    onCartClick: () -> Unit,
    onAddToCart: (Product) -> Unit,
) {
    val products by productViewModel.products.collectAsState()   // state list
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    CartFab(
                        count = cartCount,
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
                    onAddClick = {
                        onAddToCart(product)
                        scope.launch {
                            snackBarHostState.currentSnackbarData?.dismiss()
                            val result = snackBarHostState.showSnackbar(
                                message = "${product.name} added to cart",
                                actionLabel = "View Cart",
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onCartClick()
                            }
                        }
                    }
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
            onCartClick = { },
            onAddToCart = { },
            cartCount = 2,
            productViewModel = viewModel()
        )
    }
}