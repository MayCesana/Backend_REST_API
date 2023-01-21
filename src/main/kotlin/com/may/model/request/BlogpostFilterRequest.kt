package com.may.model.request

data class BlogpostFilterRequest(
    val name: String? = null,
    val text: String? = null,
    val category: String? = null,
    //val numberOfProducts: Int? = null
)
