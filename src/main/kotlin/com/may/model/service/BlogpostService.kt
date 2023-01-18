package com.may.model.service

import com.may.model.Blogpost
import com.may.model.request.BlogpostFilterRequest
import com.may.repository.BlogpostRepository
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton

@Singleton
class BlogpostService(
    private val blogpostRepository: BlogpostRepository) {

    fun create(blogpost: Blogpost) : Blogpost =
        blogpostRepository.save(blogpost);

    fun getAll(pageable: Pageable): List<Blogpost> =
        blogpostRepository.findAll(pageable)

    fun getAllIds(pageable: Pageable): List<Long> =
        blogpostRepository.findAll(pageable).map {it.id}

    fun getById(id: Long): Blogpost =
        blogpostRepository.findById(id).orElseThrow {
            HttpStatusException(
                HttpStatus.NOT_FOUND,
                "blogpost with id $id not found") }

    fun update(id: Long, blogpost: Blogpost): Blogpost {
        val old = getById(id)
        val newPost = blogpost.copy(id = old.id)

        return blogpostRepository.update(newPost)
    }

    fun delete(id: Long) {
        val blogpost = getById(id)
        blogpostRepository.delete(blogpost)
    }

    fun findAllWithFilter(pageable: Pageable, filter: BlogpostFilterRequest) : List<Blogpost> =
        when {
            filter.name != null -> findByName(pageable, filter.name)
            filter.text != null -> findByText(pageable, filter.text)
            filter.category !=null -> findByCategory(pageable, filter.category)
            else -> getAll(pageable)
        }

    private fun findByName (pageable: Pageable, name: String) : List<Blogpost> =
        blogpostRepository.findByName(pageable, name)

    private fun findByText (pageable: Pageable, text: String) : List<Blogpost> =
        blogpostRepository.findByText(pageable, text)

    private fun findByCategory (pageable: Pageable, category: String) : List<Blogpost> =
        blogpostRepository.findByCategory(pageable, category)

    private fun findByNumberOfProducts(pageable: Pageable, productsSize : Int) :List<Blogpost> =
        blogpostRepository.findByNumberOfProducts(pageable, productsSize)

}