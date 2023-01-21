package com.may.model.controller

import com.may.model.Blogpost
import com.may.model.request.BlogpostFilterRequest
import com.may.model.service.BlogpostService
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Controller("/blogposts")
class BlogpostController(
    private val blogpostService: BlogpostService) {

    /**
     * POST method - Create a product resource. The JSON body should be:
     *
     * {
     *     id: number,
     *     name: String,
     *     text: String,
     *     category: String ("Shirt", "Pants" or "TankTop"),
     *     products : array of products objects
     * }
     *
     * The response will be the created resource
     *
     */
    @Post
    fun create(blogpostRequest: Blogpost) : HttpResponse<Any>{
        print("POST /blogposts - ")
        println(blogpostRequest)
        try {
            val created = blogpostService.create(
                blogpost = blogpostRequest
            )
            println("Added blogpost:")
            println(created)
            return HttpResponse.created(created)

        }
        catch (e: Exception) {
            println("Failed to create blogpost. Error: ${e.message}")
            return HttpResponse.serverError("Failed to create blogpost. Error: " + e.message)
        }
    }

    /**
     * GET method - returns List of all the blogposts resources. The result will be an array of Blogposts
     *
     */
    @Get
    fun getAllBlogposts(@QueryValue(defaultValue = "20") limit: Int,
                       @QueryValue(defaultValue = "0") offset: Int,
    ) : List<Blogpost> {
        println("GET /blogposts")
        val pageable = Pageable.from(offset, limit)
        return blogpostService.getAll(pageable)
    }


    /**
     * GET method - returns List of the ids of the blogpost resources. The result will be an array of numbers
     *
     */
    @Get("/ids")
    fun getAllBlogpostsIds(@QueryValue(defaultValue = "20") limit: Int,
               @QueryValue(defaultValue = "0") offset: Int) : List<Long>
    {
        println("GET /blogposts/ids")
        val pageable = Pageable.from(offset, limit)
        return blogpostService.getAllIds(pageable)
    }

    /**
     * GET/{id} method - Return the blogpost with the given id.  The result will be an object of type:
     *
     * {
     *      id: number
     *      name: string,
     *      text: string,
     *      category: string ("Shirt", "Pants" or "TankTop"),
     *      products: array of products
     * }
     *
     */
    @Get("/{id}")
    fun getBlogpost(id: Long) :HttpResponse<Any>
    {
        println("GET /blogposts/${id}")
        try {
            val post = blogpostService.getById(id)
            println("blogpost found: ")
            println(post)
            return HttpResponse.ok(post)
        }
        catch (e: Exception) {
            println("Failed to get blogpost. Error: ${e.message}")
            return HttpResponse.notFound()
        }
    }


    /**
     * PUT (update) the fields for the blogpost with the given id. The input should
     * be a JSON object in the form:
     *
     * {
     *      name: string
     *      text: string
     *      category: string ("Shirt", "Pants" or "TankTop"),
     *      products: array of Products
     * }
     *
     */
    @Put("/{id}")
    fun update(
        id: Long,
        request: Blogpost
    ) : Blogpost {
        print(":PUT /blogposts - ")
        return blogpostService.update(id, request)
    }

    /**
     * DELETE method - Delete a blogpost resource, by blogpost id
     */
    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) : HttpResponse<Any> {
        println(":DELETE /blogposts/${id} - ")
        try {
            val deleted = blogpostService.delete(id)
            println("Deleted blogpost: ")
            println(deleted)
            return HttpResponse.noContent()
        }
        catch (e: Exception) {
            println("Failed to delete blogpost. error: ${e.message}")
            return HttpResponse.serverError("Failed to delete blogpost. error: ${e.message}")
        }

    }

    /**
     * Get all blogposts matching the filter - implements with POST/filter
     * Return the all blogposts matching the given filters. The JSON body should be:
     *
     * {
     *      name?: string,
     *      text?: string,
     *      category?: string ("Shirt", "Pants" or "TankTop")
     * }
     *
     */
    @Post("/filter")
    fun getAllWithFilter(
        @QueryValue(defaultValue = "20") limit: Int,
        @QueryValue(defaultValue = "0") offset: Int,
        @Body filter: BlogpostFilterRequest
    ) : List<Blogpost> {
        println(":POST /products/filter - ")
        println(filter)
        val pageable = Pageable.from(offset, limit)
        return blogpostService.findAllWithFilter(pageable, filter)
    }
}