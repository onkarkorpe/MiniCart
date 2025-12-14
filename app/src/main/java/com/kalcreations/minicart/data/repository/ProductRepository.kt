package com.kalcreations.minicart.data.repository

import com.kalcreations.minicart.R
import com.kalcreations.minicart.data.model.Product

object ProductRepository {

    fun getProducts(): List<Product> {
        return listOf(
            Product("1", image = R.drawable.headphones,"Wireless Headphones", 2999.0, null, 18, false),
            Product("2", image = R.drawable.speaker,"Bluetooth Speaker", 1499.0, 999.0, 18, true),
            Product("3", image = R.drawable.earbuds,"Earbuds (Discounted)", 799.0, 599.0, 5, true),
            Product("4", image = R.drawable.usb_cable,"USB-C Cable", 399.0, null, 5, false),
            Product("5", image = R.drawable.phone_case,"Phone Case", 499.0, null, 18, false),
            Product("6", image = R.drawable.power_bank,"Power Bank 10k", 1199.0, null, 18, false),
        )
    }
}
