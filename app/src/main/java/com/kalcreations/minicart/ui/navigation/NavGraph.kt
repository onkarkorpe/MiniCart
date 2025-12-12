package com.kalcreations.minicart.ui.navigation

/*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.minicart.ui.screens.productlist.ProductListScreen
import com.minicart.ui.screens.cart.CartScreen
import com.minicart.ui.screens.checkout.CheckoutSuccessScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.PRODUCT_LIST
    ) {
        composable(Routes.PRODUCT_LIST) {
            ProductListScreen(
                onCartClick = { navController.navigate(Routes.CART) }
            )
        }

        composable(Routes.CART) {
            CartScreen(
                onCheckoutSuccess = { navController.navigate(Routes.CHECKOUT) }
            )
        }

        composable(Routes.CHECKOUT) {
            CheckoutSuccessScreen(
                onBackToHome = { navController.navigate(Routes.PRODUCT_LIST) }
            )
        }
    }
}

 */
