package com.monzo.androidtest.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ArticleRemoteService {
    @GET("search")
    fun searchArticles(
        @Query("q") searchTerm: String = "fintech,brexit",
        @Query("show-fields") showFields: String = "headline,thumbnail",
        @Query("page") page: Int = 1,
        @Query("page-size") pageSize: Int = 20
    ): Single<NetworkArticleResponse>

    /**
     * The id of the article would be better suited for retrieving the article when
     * we start driving the UI from the DB. However, for now it's safer to use apiUrl for network
     * retrieval.
     */
    @GET
    fun getArticle(
        @Url articleUrl: String,
        @Query("show-fields") showFields: String = "headline,thumbnail,body"
    ): Single<NetworkArticle>
}
