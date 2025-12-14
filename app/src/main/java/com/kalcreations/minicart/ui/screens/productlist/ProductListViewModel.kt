package com.kalcreations.minicart.ui.screens.productlist

import androidx.lifecycle.ViewModel
import com.kalcreations.minicart.data.model.Product
import com.kalcreations.minicart.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductListViewModel : ViewModel() {

    private val _products = MutableStateFlow(
        ProductRepository.getProducts()
    )

    val products : StateFlow<List<Product>> = _products.asStateFlow()

}

