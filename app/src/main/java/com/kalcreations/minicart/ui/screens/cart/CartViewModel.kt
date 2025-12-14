package com.kalcreations.minicart.ui.screens.cart

import androidx.lifecycle.ViewModel
import com.kalcreations.minicart.data.model.CartItem
import com.kalcreations.minicart.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    // STEP 3: Cart operations only

    fun addToCart(product: Product) {
        val currentItems = _uiState.value.cartItems.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst {
            it.product.id == product.id
        }

        if (existingItemIndex >= 0) {
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] =
                existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            currentItems.add(CartItem(product = product, quantity = 1))
        }

        _uiState.value = _uiState.value.copy(cartItems = currentItems)
    }

    fun increaseQuantity(productId: String) {
        val updatedItems = _uiState.value.cartItems.map { item ->
            if (item.product.id == productId) {
                item.copy(quantity = item.quantity + 1)
            } else item
        }

        _uiState.value = _uiState.value.copy(cartItems = updatedItems)
    }

    fun decreaseQuantity(productId: String) {
        val updatedItems = _uiState.value.cartItems.mapNotNull { item ->
            if (item.product.id == productId) {
                if (item.quantity > 1) {
                    item.copy(quantity = item.quantity - 1)
                } else {
                    null // remove item if quantity becomes 0
                }
            } else item
        }

        _uiState.value = _uiState.value.copy(cartItems = updatedItems)
    }

    fun removeItem(productId: String) {
        val updatedItems = _uiState.value.cartItems.filterNot {
            it.product.id == productId
        }

        _uiState.value = _uiState.value.copy(cartItems = updatedItems)
    }
}

