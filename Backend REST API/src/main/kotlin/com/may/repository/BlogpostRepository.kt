package com.may.repository

import com.may.model.Blogpost
import io.micronaut.data.model.Pageable
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface BlogpostRepository : CrudRepository<Blogpost, Long> {
    fun findAll(pageable: Pageable): List<Blogpost>

    @MongoFindQuery("{text: :text}")
    fun findByText(pageable: Pageable, text: String) : List<Blogpost>

    @MongoFindQuery("{text: {\$regex: :text, \$options: 'i'}}")
    fun findByTextLike(pageable: Pageable, text: String) : List<Blogpost>

    @MongoFindQuery("{name: :name}")
    fun findByName(pageable: Pageable, name: String) : List<Blogpost>

    @MongoFindQuery("{name: {\$regex: :name, \$options: 'i'}}")
    fun findByNameLike(pageable: Pageable, name: String) : List<Blogpost>

    @MongoFindQuery("{category: :category}")
    fun findByCategory(pageable: Pageable, category: String) : List<Blogpost>

    @MongoFindQuery("{products: { \$size: :size }}")
    fun findByNumberOfProducts(pageable: Pageable, size: Int) : List<Blogpost>
}