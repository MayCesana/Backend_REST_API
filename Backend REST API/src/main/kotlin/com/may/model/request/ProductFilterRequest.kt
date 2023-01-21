package com.may.model.request
import com.may.model.Category

data class ProductFilterRequest(
    val name: String? = null,
    val brand: String?=null,
    val price: String?=null,
    val category: Category?=null
)
