package com.spring.project.repository

import com.spring.project.entity.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface UserRepository : MongoRepository<User, ObjectId> {
    fun findByUsername(username: String): User?
}