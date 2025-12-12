package com.kalcreations.minicart.ui.screens.productlist

import androidx.lifecycle.ViewModel
import com.kalcreations.minicart.data.model.Product
import com.kalcreations.minicart.data.repository.ProductRepository

class ProductListViewModel : ViewModel() {

    val products: List<Product> = ProductRepository.getProducts()

    var cartCount = 0
        private set

    fun addToCart(product: Product) {
        cartCount += 1
    }
}

