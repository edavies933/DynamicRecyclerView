package com.example.emmanueldaviest.data.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.emmanueldaviest.domain.model.Teaser

@Database(entities = [Teaser::class], version = 2)
abstract class TeaserDatabase  : RoomDatabase() {

    abstract fun tripDatabaseDao(): TeaserDao
}