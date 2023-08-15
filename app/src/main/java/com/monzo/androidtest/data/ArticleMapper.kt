package com.monzo.androidtest.data

import com.monzo.androidtest.data.remote.NetworkArticle
import java.util.Calendar

/**
 * Data mapping functions that maps from the data types specific to the
 * network and DB to a type with data relevant to higher layers in the
 * arch. hierarchy i.e presentation layers.
 */

/**
 * Converts each model in a list of network models to a model external to the data layer
 * and returns a list of said models for persisting into a DB i.e. Room.
 */
fun List<NetworkArticle>.toExternalList() = map(NetworkArticle::toExternalList)

/**
 * Takes a list of NetworkArticles, groups them by the week they were published
 * returns a list of the groups.
 */
fun List<NetworkArticle>.toExternalGroupList(): List<ArticleGroupItem> {
    return this.groupBy { networkArticle ->
        val calendar = Calendar.getInstance()
        calendar.time = networkArticle.webPublicationDate
        calendar[Calendar.WEEK_OF_YEAR]
    }.entries.map { (week, networkArticleListForWeek) ->
        ArticleGroupItem(week, nestedArticleList = networkArticleListForWeek.toExternalList())
    }.sortedByDescending { it.publishedWeek }
}

/**
 * Converts a network model to a model used
 * by higher hierarchy layers external to the data layer.
 */
fun NetworkArticle.toExternalList() = ArticleListItem(
    id = id,
    apiUrl = apiUrl,
    thumbnail = fields.thumbnail,
    headline = fields.headline,
    publicationDate = webPublicationDate
)
