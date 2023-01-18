package com.may.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Blogpost(
    @field: Id
    //@field: GeneratedValue
    val id: Long,
    val name: String,
    val text: String,
    val category: String,
    val products : List<Product>
)
