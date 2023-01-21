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

    fun findAllWithFilter(pageable: Pageable, filter: BlogpostFilterRequest) : List<Blogpost> {
        return filter(findByName(pageable, filter.name),
            findByText(pageable,filter.text), findByCategory(pageable,filter.category.toString()))
    }


    private fun filter(filteredByName: List<Blogpost>, filteredByText: List<Blogpost>, filteredByCategory: List<Blogpost>) : List<Blogpost> {
        var res : Set<Blogpost> = filteredByName.toSet()
        res  = res.intersect(filteredByText)
        res = res.intersect(filteredByCategory)
        return res.toList()
    }

    private fun findByName (pageable: Pageable, name: String?) : List<Blogpost> =
        when {
            name !=null -> blogpostRepository.findByName(pageable, name)
            else -> getAll(pageable)
        }

    private fun findByText (pageable: Pageable, text: String?) : List<Blogpost> =
        when {
            text !=null -> blogpostRepository.findByText(pageable, text)
            else -> getAll(pageable)
        }

    private fun findByCategory(pageable: Pageable, category: String?) : List<Blogpost> {

        when {
            category != null -> blogpostRepository.findByCategory(pageable, category)
            else -> getAll(pageable)
        }
        return getAll(pageable)
    }

    private fun findByNumberOfProducts(pageable: Pageable, productsSize : Int) :List<Blogpost> =
        blogpostRepository.findByNumberOfProducts(pageable, productsSize)

}