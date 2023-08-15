package com.monzo.androidtest.data

import java.util.Date

/**
 * Article type with information relevant to higher layers external to the data layer
 * and is used only within said layers e.g. NestedArticleList in the presentation.
 */
data class ArticleListItem(
        val id: String,
        val apiUrl: String,
        val thumbnail: String,
        val headline: String,
        val publicationDate: Date,
)

/**
 * [ArticleListItem]s categorised by their [publishedWeek]
 * for use within the presentation layer.
 *
 * @param publishedWeek 0 is this week, 1 is last week and so on.
 */
data class ArticleGroupItem(
        val publishedWeek: Int,
        val nestedArticleList: List<ArticleListItem>
)

/**
 * Article type with information relevant to higher layers external to the data layer
 * and is used only within said layers e.g. Article in the presentation.
 */
data class ArticleDetailItem(
        val id: String,
        val apiUrl: String,
        val thumbnail: String,
        val headline: String,
        val body: String
)
