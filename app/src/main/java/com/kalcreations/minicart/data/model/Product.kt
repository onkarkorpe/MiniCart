package com.kalcreations.minicart.data.model

data class Product(
    val id: String,
    val image: Int? = null,
    val name: String,
    val originalPrice: Double,
    val preDiscountPrice: Double?,   // null means no discount
    val taxGroupPercent: Int,        // 5 or 18
    val isPreDiscounted: Boolean     // true if preDiscountPrice != null
)