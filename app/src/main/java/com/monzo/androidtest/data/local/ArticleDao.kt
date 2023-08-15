package com.monzo.androidtest.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ArticleDao {
    @Upsert
    fun insertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleById(id: String): Single<ArticleEntity>

    @Query("SELECT * FROM articles ORDER BY publication_date DESC")
    fun getPagedArticlesByPublishedDate(): List<ArticleEntity> // TODO Replace with PagingSource

    @Query("DELETE FROM articles")
    fun clearArticles(): Completable
}
