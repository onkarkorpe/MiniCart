package com.kalcreations.minicart.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kalcreations.minicart.ui.screens.cart.CartScreen
import com.kalcreations.minicart.ui.screens.cart.CartViewModel
import com.kalcreations.minicart.ui.screens.productlist.ProductListScreen
import com.kalcreations.minicart.ui.screens.productlist.ProductListViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val cartViewModel: CartViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.PRODUCT_LIST
    ) {
        composable(Routes.PRODUCT_LIST) {
            val productViewModel: ProductListViewModel = viewModel()
            ProductListScreen(
                productViewModel = productViewModel,
                cartCount = cartViewModel.uiState
                    .collectAsState().value.cartItems.sumOf { it.quantity },
                onCartClick = { navController.navigate(Routes.CART) },
                onAddToCart = { product ->
                    cartViewModel.addToCart(product)
                }
            )
        }

        composable(Routes.CART) {
            CartScreen(
                onBack = { navController.navigate(Routes.PRODUCT_LIST) },
                onCheckoutClick = { navController.navigate(Routes.CHECKOUT) },
                onContinueShopping = { navController.navigate(Routes.PRODUCT_LIST)},
                viewModel = cartViewModel
            )
        }

        composable(Routes.CHECKOUT) {
//            CheckoutSuccessScreen(
//                onBackToHome = { navController.navigate(Routes.PRODUCT_LIST) }
//            )
        }
    }
}

