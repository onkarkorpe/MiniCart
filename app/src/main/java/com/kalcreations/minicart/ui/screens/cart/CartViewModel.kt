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

    fun addToCart(product: Product) {
        val currentItems = _uiState.value.cartItems.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == product.id }
        val subtotal = calculateSubtotal(currentItems)
        val tax = calculateTax(currentItems)
        val (isApplicable, reason) =
            getCouponEligibility(currentItems, subtotal)

        if (index >= 0) {
            val item = currentItems[index]
            currentItems[index] = item.copy(quantity = item.quantity + 1)
        } else {
            currentItems.add(CartItem(product, 1))
        }

        _uiState.value = _uiState.value.copy(
            cartItems = currentItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = subtotal + tax,
            isCouponApplicable = isApplicable,
            couponDisabledReason = reason
        )
    }

    fun increaseQuantity(productId: String) {
        val updatedItems = _uiState.value.cartItems.map { item ->
            if (item.product.id == productId) {
                item.copy(quantity = item.quantity + 1)
            } else item
        }

        val subtotal = calculateSubtotal(updatedItems)
        val tax = calculateTax(updatedItems)
        val (isApplicable, reason) =
            getCouponEligibility(updatedItems, subtotal)


        _uiState.value = _uiState.value.copy(
            cartItems = updatedItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = subtotal + tax,
            isCouponApplicable = isApplicable,
            couponDisabledReason = reason
        )
    }

    fun decreaseQuantity(productId: String) {
        val updatedItems = _uiState.value.cartItems.mapNotNull { item ->
            if (item.product.id == productId) {
                if (item.quantity > 1) {
                    item.copy(quantity = item.quantity - 1)
                } else null
            } else item
        }

        val subtotal = calculateSubtotal(updatedItems)
        val tax = calculateTax(updatedItems)
        val (isApplicable, reason) =
            getCouponEligibility(updatedItems, subtotal)

        _uiState.value = _uiState.value.copy(
            cartItems = updatedItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = subtotal + tax,
            isCouponApplicable = isApplicable,
            couponDisabledReason = reason
        )
    }

    fun removeItem(productId: String) {
        val updatedItems = _uiState.value.cartItems.filterNot {
            it.product.id == productId
        }

        val subtotal = calculateSubtotal(updatedItems)
        val tax = calculateTax(updatedItems)
        val (isApplicable, reason) =
            getCouponEligibility(updatedItems, subtotal)

        _uiState.value = _uiState.value.copy(
            cartItems = updatedItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = subtotal + tax,
            isCouponApplicable = isApplicable,
            couponDisabledReason = reason
        )
    }

    private fun calculateSubtotal(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { item ->
            val unitPrice =
                item.product.preDiscountPrice ?: item.product.originalPrice
            unitPrice * item.quantity
        }
    }

    private fun calculateTax(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { item ->
            val unitPrice =
                item.product.preDiscountPrice ?: item.product.originalPrice

            val itemTotal = unitPrice * item.quantity
            val taxRate = item.product.taxGroupPercent / 100.0

            itemTotal * taxRate
        }
    }

    private fun hasNonDiscountedItem(cartItems: List<CartItem>): Boolean {
        return cartItems.any { !it.product.isPreDiscounted }
    }

    private fun getCouponEligibility(
        cartItems: List<CartItem>,
        subtotal: Double
    ): Pair<Boolean, String?> {

        if (cartItems.isEmpty()) {
            return false to null
        }

        if (subtotal < 1000) {
            return false to "Add items worth ₹1000 to apply this coupon"
        }

        if (!hasNonDiscountedItem(cartItems)) {
            return false to "Coupon not applicable on discounted items"
        }

        return true to null
    }

    private fun calculateCouponDiscount(cartItems: List<CartItem>): Double {
        val eligibleAmount = cartItems
            .filter { !it.product.isPreDiscounted }
            .sumOf { item ->
                item.product.originalPrice * item.quantity
            }

        val discount = eligibleAmount * 0.20
        return discount.coerceAtMost(300.0)
    }

    fun applyCoupon() {
        val currentState = _uiState.value

        if (!currentState.isCouponApplicable || currentState.isCouponApplied) {
            return
        }

        val discount = calculateCouponDiscount(currentState.cartItems)

        val finalAmount =
            currentState.subtotal - discount + currentState.taxTotal

        val message = when {
            discount == 300.0 ->
                "Coupon applied. Maximum discount of ₹300 used."
            currentState.cartItems.any { it.product.isPreDiscounted } ->
                "Coupon applied. Discount applied only on non-discounted items."
            else ->
                "Coupon applied successfully."
        }

        _uiState.value = currentState.copy(
            isCouponApplied = true,
            couponDiscount = discount,
            finalAmount = finalAmount,
            couponMessage = message
        )
    }

}

