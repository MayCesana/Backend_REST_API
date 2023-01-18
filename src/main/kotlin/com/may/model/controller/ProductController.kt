package com.may.model.controller

import com.may.model.Product
import com.may.model.request.ProductFilterRequest
import com.may.model.service.ProductService
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Controller("/products")
class ProductController(private val productService: ProductService)
{
    //Create method - Post Http request to create a new product
    @Post
    @Status(HttpStatus.CREATED)
    fun create(productRequest: Product) =
        productService.create(
            product = productRequest
        )

    //Read method - get all products ids
    @Get
    fun getAllProducts(@QueryValue(defaultValue = "20") limit: Int,
               @QueryValue(defaultValue = "0") offset: Int,
               ) : List<Long> {
        val pageable = Pageable.from(offset, limit)
        return productService.findAllIds(pageable)
    }

    //read method - get single product by id
    @Get("/{id}")
    fun getProduct(@PathVariable id: Long) =
        productService.getById(id)

    //update one product method - Put Http request
    @Put("/{id}")
    fun update(
        id: Long,
        request: Product
    ) : Product {
        return productService.update(id, request)
    }

    //delete method to delete one product
    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) {
        productService.delete(id)
    }

    //get with filter
    @Post("/filter")
    fun getAllWithFilter(
        @QueryValue(defaultValue = "20") limit: Int,
        @QueryValue(defaultValue = "0") offset: Int,
        @Body productFilter: ProductFilterRequest) : List<Product> {
        val pageable = Pageable.from(offset, limit)
        return productService.findAllWithFilter(pageable, productFilter)
    }
}