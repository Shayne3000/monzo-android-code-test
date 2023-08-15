package com.monzo.androidtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ArticleDatabase : RoomDatabase() {
    // TODO Add hilt module for DB
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "news_db"
    }
}
