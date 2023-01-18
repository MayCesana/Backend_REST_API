package com.may.repository

import com.may.model.Product
import io.micronaut.data.model.Pageable
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor

@MongoRepository
interface ProductRepository : CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    fun findAll(pageable: Pageable): List<Product>

    @MongoFindQuery("{price: :price}")
    fun findByPrice(pageable: Pageable, price: String) : List<Product>

    @MongoFindQuery("{name: :name}")
    fun findByName(pageable: Pageable, name: String) : List<Product>

    @MongoFindQuery("{name: {\$regex: :name, \$options: 'i'}}")
    fun findByNameLike(pageable: Pageable, name: String) : List<Product>

    @MongoFindQuery("{brand: :brand}")
    fun findByBrand(pageable: Pageable, brand: String) : List<Product>

    @MongoFindQuery("{category: :category}")
    fun findByCategory(pageable: Pageable, category: String) : List<Product>

}