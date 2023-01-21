package com.may.model.controller

import com.may.model.Product
import com.may.model.request.ProductFilterRequest
import com.may.model.service.ProductService
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*

@Controller("/products")
class ProductController(private val productService: ProductService)
{
    /**
     * POST method - Create a product resource. The JSON body should be:
     *
     * {
     *      id: number,
     *      name: string,
     *      brand: string,
     *      price: string,
     *      category: string ("Shirt", "Pants" or "TankTop")
     * }
     *
     * The response will be the created resource
     *
     */
    @Post
    fun create(productRequest: Product) : HttpResponse<Any> {
        print("POST /products - ")
        println(productRequest)
        try {
            val created = productService.create(product = productRequest)
            println("Added product:")
            println(created)
            return HttpResponse.created(created)

        }
        catch (e: Exception) {
            println("Failed to create product. Error: ${e.message}")
            return HttpResponse.serverError("Failed to create product. Error: " + e.message)
        }
    }


    /**
     * GET method - returns List of all the products resources. The result will be an array of Products
     *
     */
    @Get
    fun getAllProducts(@QueryValue(defaultValue = "20") limit: Int,
               @QueryValue(defaultValue = "0") offset: Int,
               ) : List<Product> {
        println("GET /products")
        val pageable = Pageable.from(offset, limit)
        return productService.findAll(pageable)
    }

    /**
     * GET method - returns List of the ids of the products resources. The result will be an array of numbers
     *
     */
    @Get("/ids")
    fun getAllProductsIds(@QueryValue(defaultValue = "20") limit: Int,
                       @QueryValue(defaultValue = "0") offset: Int,
    ) : List<Long> {
        println("GET /products/ids")
        val pageable = Pageable.from(offset, limit)
        return productService.findAllIds(pageable)
    }

    /**
     * GET/{id} method - Return the product with the given id.  The result will be an object of type:
     *
     * {
     *      id: number
     *      name: string,
     *      brand: string,
     *      price: string,
     *      category: string ("Shirt", "Pants" or "TankTop")
     *
     * }
     *
     */
    @Get("/{id}")
    fun getProduct(@PathVariable id: Long) : HttpResponse<Any> {
        println("GET /products/${id}")
        try {
            val product = productService.getById(id)
            println("product found: ")
            println(product)
            return HttpResponse.ok(product)
        }
        catch (e: Exception) {
            println("Failed to get product. Error: ${e.message}")
            return HttpResponse.notFound("Failed to get product. Error: ${e.message}")
        }
    }


    /**
     * PUT (update) the fields for the product with the given id. The input should
     * be a JSON object in the form:
     *
     * {
     *      name: string
     *      brand: string
     *      price: string
     *     category: string ("Shirt", "Pants" or "TankTop")
     * }
     *
     */
    @Put("/{id}")
    fun update(
        id: Long,
        request: Product
    ): Product {

        print(":PUT /products - ")
        println(request)
        return productService.update(id, request)
    }

    /**
     * DELETE method - Delete a product resource, by product id
     */
    @Delete("/{id}")
    //@Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) : HttpResponse<Any> {
        println(":DELETE /products/${id} - ")
        try {
            val deleted = productService.delete(id)
            println("Deleted product: ")
            println(deleted)
            return HttpResponse.noContent()
        }
        catch (e: Exception) {
            println("Failed to delete product. error: ${e.message}")
            return HttpResponse.serverError("Failed to delete product. error: ${e.message}")
        }
    }

    /**
     * Get all products matching the filter - implements with POST/filter
     * Return the all products matching the given filters. The JSON body should be:
     *
     * {
     *      name?: string,
     *      brand?: string,
     *      price?: string,
     *      category?: string ("Shirt", "Pants" or "TankTop")
     * }
     *
     */
    @Post("/filter")
    fun getAllWithFilter(
        @QueryValue(defaultValue = "20") limit: Int,
        @QueryValue(defaultValue = "0") offset: Int,
        @Body productFilter: ProductFilterRequest) : List<Product> {
        println(":POST /products/filter - ")
        println(productFilter)
        val pageable = Pageable.from(offset, limit)
        return productService.findAllWithFilter(pageable, productFilter)
    }
}