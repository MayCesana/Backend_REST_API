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

    fun findAllWithFilter(pageable: Pageable, filter: ProductFilterRequest) : List<Product>
    {
        return filter(findByName(pageable, filter.name), findByPrice(pageable, filter.price),
            findByBrand(pageable,filter.brand), findByCategory(pageable,filter.category.toString()))
    }

    private fun filter(filteredByName: List<Product>, filteredByPrice: List<Product>, filteredByBrand: List<Product>, filteredByCategory: List<Product>) : List<Product> {
        var res : Set<Product> = filteredByName.toSet()
        res  = res.intersect(filteredByBrand)
        res = res.intersect(filteredByPrice)
        res = res.intersect(filteredByCategory)
        return res.toList()
    }

    private fun findByName (pageable: Pageable, name: String?) : List<Product> =
        when {
            name !=null -> productRepository.findByName(pageable, name)
            else -> findAll(pageable)
        }

    private fun findByPrice (pageable: Pageable, price: String?) : List<Product> =
        when
        {
            price != null -> productRepository.findByPrice(pageable, price)
            else -> findAll(pageable)
        }

    private fun findByBrand(pageable: Pageable, brand: String?) : List<Product> =
        when {
            brand != null -> productRepository.findByBrand(pageable, brand)
            else -> findAll(pageable)
        }

    private fun findByCategory(pageable: Pageable, category: String?) : List<Product> {
        when {
            category != null -> productRepository.findByCategory(pageable, category)
            else -> findAll(pageable)
        }
        return findAll(pageable)
    }

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
        if(id !=null) {
            val product = getById(id)
            productRepository.delete(product)
        }
    }
}

