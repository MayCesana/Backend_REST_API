package com.may.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

enum class Category {
    Pants, Shirt, TankTop
}

@MappedEntity
data class Product(
    @field:Id
    //@field: GeneratedValue
    val id: Long,
    val name: String,
    val brand: String,
    val price: String,
    val category: Category

)
