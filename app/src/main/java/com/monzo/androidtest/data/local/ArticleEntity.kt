package com.monzo.androidtest.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Data type for an article stored locally in the Room DB and represents a row in the articles table.
 * This model is internal to the data layer and is not exposed to higher layers in the hierarchy.
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val thumbnail: String,
    @ColumnInfo(name = "section_id")
    val sectionId: String,
    @ColumnInfo(name = "section_name")
    val sectionName: String,
    val title: String,
    val url: String,
    val headline: String,
    val body: String,
    @ColumnInfo(name = "publication_date")
    val publicationDate: Date
)
