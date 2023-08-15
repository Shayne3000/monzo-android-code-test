package com.monzo.androidtest.data.remote

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class NetworkArticleResponse(val response: NetworkArticleResults)

@JsonClass(generateAdapter = true)
data class NetworkArticleResults(val results: List<NetworkArticle>)

/**
 * Data type for an article retrieved from the remote Guardian API service that is internal
 * to the data layer.
 */
@JsonClass(generateAdapter = true)
data class NetworkArticle(
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: Date,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val fields: NetworkArticleFields
)

@JsonClass(generateAdapter = true)
data class NetworkArticleFields(
    val headline: String,
    val main: String? = null,
    val body: String? = null,
    val thumbnail: String
)
