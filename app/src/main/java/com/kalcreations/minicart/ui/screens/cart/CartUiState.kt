package com.kalcreations.minicart.ui.screens.cart

import com.kalcreations.minicart.data.model.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),

    // Coupon related
    val isCouponApplied: Boolean = false,
    val isCouponApplicable: Boolean = false,
    val couponDisabledReason: String? = null,
    val couponDiscount: Double = 0.0,

    val couponInlineMessage: String? = null,
    val snackBarMessage: String? = null,

    // Totals
    val subtotal: Double = 0.0,
    val taxTotal: Double = 0.0,
    val finalAmount: Double = 0.0
)
