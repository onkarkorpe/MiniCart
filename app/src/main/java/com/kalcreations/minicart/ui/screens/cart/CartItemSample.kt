package com.kalcreations.minicart.ui.screens.cart

import com.kalcreations.minicart.R
import com.kalcreations.minicart.data.model.CartItem
import com.kalcreations.minicart.data.model.Product

object CartItemSample {

    val wirelessHeadphone = CartItem(
        Product(
            "1", R.drawable.wirelessheadphone, "Wireless Headphones",2999.0, null, 18, false),
        quantity = 1
    )

    val bluetoothSpeaker = CartItem(
        Product("2", R.drawable.wirelessheadphone, "Bluetooth Speaker",1499.0, 999.0, 18, true),
        quantity = 2
    )

    val phoneCase = CartItem(
        Product(
            "1", R.drawable.wirelessheadphone, "Phone Case",499.0, null, 18, false),
        quantity = 3
    )

    val powerBank = CartItem(
        Product("2", R.drawable.wirelessheadphone, "Power Bank",1199.0, 999.0, 18, true),
        quantity = 2
    )

    val earbuds = CartItem(
        Product("2", R.drawable.wirelessheadphone, "Earbuds",799.0, 999.0, 18, true),
        quantity = 2
    )
}
