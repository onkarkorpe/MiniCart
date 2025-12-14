package com.kalcreations.minicart.ui.screens.cart

import androidx.lifecycle.ViewModel
import com.kalcreations.minicart.data.model.CartItem
import com.kalcreations.minicart.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.math.RoundingMode

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun addToCart(product: Product) {
        val currentItems = _uiState.value.cartItems.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == product.id }

        if (index >= 0) {
            val item = currentItems[index]
            currentItems[index] = item.copy(quantity = item.quantity + 1)
        } else {
            currentItems.add(CartItem(product, 1))
        }

        val subtotal = calculateSubtotal(currentItems)
        val tax = calculateTax(currentItems)
        val (isApplicable, reason) =
            getCouponEligibility(currentItems, subtotal)
        val finalAmount = roundToTwoDecimals(subtotal + tax)

        _uiState.value = _uiState.value.copy(
            cartItems = currentItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = finalAmount,
            isCouponApplied = false,
            couponDiscount = 0.0,
            couponInlineMessage = null,
            snackBarMessage = null,
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
        val finalAmount = roundToTwoDecimals(subtotal + tax)


        _uiState.value = _uiState.value.copy(
            cartItems = updatedItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = finalAmount,
            isCouponApplied = false,
            couponDiscount = 0.0,
            couponInlineMessage = null,
            snackBarMessage = null,
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
        val finalAmount = roundToTwoDecimals(subtotal + tax)

        _uiState.value = _uiState.value.copy(
            cartItems = updatedItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = finalAmount,
            isCouponApplied = false,
            couponDiscount = 0.0,
            couponInlineMessage = null,
            snackBarMessage = null,
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
        val finalAmount = roundToTwoDecimals(subtotal + tax)

        _uiState.value = _uiState.value.copy(
            cartItems = updatedItems,
            subtotal = subtotal,
            taxTotal = tax,
            finalAmount = finalAmount,
            isCouponApplied = false,
            couponDiscount = 0.0,
            couponInlineMessage = null,
            snackBarMessage = null,
            isCouponApplicable = isApplicable,
            couponDisabledReason = reason
        )
    }

    private fun calculateSubtotal(cartItems: List<CartItem>): Double {
        val subtotal = cartItems.sumOf { item ->
            val unitPrice =
                item.product.preDiscountPrice ?: item.product.originalPrice
            unitPrice * item.quantity
        }
        return roundToTwoDecimals(subtotal)
    }

    private fun calculateTax(cartItems: List<CartItem>): Double {
        val tax =  cartItems.sumOf { item ->
            val unitPrice =
                item.product.preDiscountPrice ?: item.product.originalPrice

            val itemTotal = unitPrice * item.quantity
            val taxRate = item.product.taxGroupPercent / 100.0

            itemTotal * taxRate
        }
        return roundToTwoDecimals(tax)
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
        return roundToTwoDecimals(discount.coerceAtMost(300.0))
    }


    fun applyCoupon() {
        val currentState = _uiState.value

        if (!currentState.isCouponApplicable || currentState.isCouponApplied) {
            return
        }

        val discount = calculateCouponDiscount(currentState.cartItems)

        val finalAmount =
            roundToTwoDecimals(currentState.subtotal - discount + currentState.taxTotal)

        val inlineMessage = when {
            discount == 300.0 ->
                "Coupon applied. Maximum discount of ₹300 used."
            currentState.cartItems.any { it.product.isPreDiscounted } ->
                "Coupon applied. Discount applied only on non-discounted items."
            else ->
                "Coupon applied. You saved ₹$discount"
        }

        _uiState.value = currentState.copy(
            isCouponApplied = true,
            couponDiscount = discount,
            finalAmount = finalAmount,
            couponInlineMessage = inlineMessage,
            snackBarMessage = "Coupon Applied Successfully"
        )
    }

    private fun roundToTwoDecimals(value: Double): Double {
        return BigDecimal(value)
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()
    }

    fun removeCoupon() {
        val currentState = _uiState.value

        val finalAmount =
            roundToTwoDecimals(currentState.subtotal + currentState.taxTotal)

        // Recalculate eligibility again
        val (isApplicable, reason) =
            getCouponEligibility(
                currentState.cartItems,
                subtotal = currentState.subtotal
            )

        _uiState.value = currentState.copy(
            isCouponApplied = false,
            couponDiscount = 0.0,
            couponInlineMessage = null,
            snackBarMessage = null,
            finalAmount = finalAmount,
            isCouponApplicable = isApplicable,
            couponDisabledReason = reason
        )
    }

    fun checkout() {
        _uiState.value = CartUiState()
    }
}

