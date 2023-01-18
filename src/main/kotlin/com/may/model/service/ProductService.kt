package com.may.model.service

import com.may.model.Product
import com.may.model.request.ProductFilterRequest
import com.may.repository.ProductRepository
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton

@Singleton
class ProductService (
    private val productRepository : ProductRepository) {

    fun create(product: Product) : Product =
      productRepository.save(product)

    fun findAll(pageable: Pageable): List<Product> =
        productRepository.findAll(pageable)

    fun findAllIds(pageable: Pageable) : List<Long> =
        productRepository.findAll(pageable).map {it.id}

    fun findAllWithFilter(pageable: Pageable, filter: ProductFilterRequest) : List<Product> =
        when {
            filter.name !=null -> findByName(pageable, filter.name)
            filter.brand != null -> findByBrand(pageable, filter.brand)
            filter.price != null -> findByPrice(pageable, filter.price)
            filter.category !=null -> findByCategory(pageable, filter.category.toString())
            else -> findAll(pageable)
        }

    private fun findByName (pageable: Pageable, name: String) : List<Product> =
        productRepository.findByName(pageable, name)

    private fun findByPrice (pageable: Pageable, price: String) : List<Product> =
        productRepository.findByPrice(pageable, price)

    private fun findByBrand(pageable: Pageable, brand: String) : List<Product> =
        productRepository.findByBrand(pageable, brand)

    private fun findByCategory(pageable: Pageable, category: String) : List<Product> =
        productRepository.findByCategory(pageable, category)

    fun getById(id: Long): Product =
        productRepository.findById(id).orElseThrow {
            HttpStatusException(HttpStatus.NOT_FOUND,
                "product with id $id not found") }

    fun update(id: Long, product: Product): Product {
        val old = getById(id)
        val newProduct = product.copy(id = old.id)

        return productRepository.update(newProduct)
    }

    fun delete(id: Long) {
        val product = getById(id)
        productRepository.delete(product)
    }
}

