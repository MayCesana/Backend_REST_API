package com.may.model.controller

import com.may.model.Blogpost
import com.may.model.Product
import com.may.model.request.BlogpostFilterRequest
import com.may.model.request.ProductFilterRequest
import com.may.model.service.BlogpostService
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Controller("/blogposts")
class BlogpostController(
    private val blogpostService: BlogpostService) {

    @Post
    @Status(HttpStatus.CREATED)
    fun create(blogpostRequest: Blogpost) =
        blogpostService.create(
            blogpost = blogpostRequest
        )

    //READ operation - get all blogpost, return list of ids of blogposts
    @Get
    fun getAllBlogposts(@QueryValue(defaultValue = "20") limit: Int,
               @QueryValue(defaultValue = "0") offset: Int) : List<Long>
    {
        val pageable = Pageable.from(offset, limit)
        return blogpostService.getAllIds(pageable)
    }

    @Get("/{id}")
    fun getBlogpost(id: Long) =
        blogpostService.getById(id)

    @Put("/{id}")
    fun update(
        id: Long,
        request: Blogpost
    ) : Blogpost {
        return blogpostService.update(id, request)
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) {
        blogpostService.delete(id)
    }

    //get with filter
    @Post("/filter")
    fun getAllWithFilter(
        @QueryValue(defaultValue = "20") limit: Int,
        @QueryValue(defaultValue = "0") offset: Int,
        @Body filter: BlogpostFilterRequest
    ) : List<Blogpost> {
        val pageable = Pageable.from(offset, limit)
        return blogpostService.findAllWithFilter(pageable, filter)
    }
}