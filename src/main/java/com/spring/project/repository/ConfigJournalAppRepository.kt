package com.spring.project.repository

import com.spring.project.entity.ConfigJournalApp
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface ConfigJournalAppRepository : MongoRepository<ConfigJournalApp, ObjectId> {
}