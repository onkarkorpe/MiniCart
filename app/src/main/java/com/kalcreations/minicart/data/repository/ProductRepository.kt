package com.kalcreations.minicart.data.repository

import com.kalcreations.minicart.R
import com.kalcreations.minicart.data.model.Product

object ProductRepository {

    fun getProducts(): List<Product> {
        return listOf(
            Product("1", image = R.drawable.wirelessheadphone,"Wireless Headphones", 2999.0, null, 18, false),
            Product("2", image = null,"Bluetooth Speaker", 1499.0, 999.0, 18, true),
            Product("3", image = null,"USB-C Cable", 399.0, null, 5, false),
            Product("4", image = null,"Phone Case", 499.0, null, 18, false),
            Product("5", image = null,"Power Bank 10k", 1199.0, null, 18, false),
            Product("6", image = null,"Earbuds (Discounted)", 799.0, 599.0, 5, true)
        )
    }
}
