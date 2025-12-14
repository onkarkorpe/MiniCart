package com.kalcreations.minicart.ui.screens.cart

import com.kalcreations.minicart.data.model.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),

    // Coupon related (logic later)
    val isCouponApplied: Boolean = false,
    val couponDiscount: Double = 0.0,
    val couponMessage: String? = null,

    // Totals (calculated later)
    val subtotal: Double = 0.0,
    val taxTotal: Double = 0.0,
    val finalAmount: Double = 0.0
)
