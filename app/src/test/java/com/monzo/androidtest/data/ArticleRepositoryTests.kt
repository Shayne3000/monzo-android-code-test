package com.monzo.androidtest.data

import com.monzo.androidtest.data.remote.ArticleService
import com.monzo.androidtest.data.remote.NetworkArticle
import com.monzo.androidtest.data.remote.NetworkArticleFields
import com.monzo.androidtest.data.remote.NetworkArticleResponse
import com.monzo.androidtest.data.remote.NetworkArticleResults
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class ArticleRepositoryTests {
    private val articleApi: ArticleService = mock()
    private val fields: NetworkArticleFields = NetworkArticleFields("", null, null, "")
    private val networkArticleResults = NetworkArticleResults(
        results = listOf(
            NetworkArticle(
                "test",
                "",
                "",
                Calendar.getInstance().time,
                "",
                "",
                "",
                fields
            )
        )
    )
    private val networkResponse: NetworkArticleResponse =
        NetworkArticleResponse(response = networkArticleResults)
    private lateinit var articleRepository: ArticleRepository

    @Before
    fun setup() {
        articleRepository =
            ArticleRepository(articleApi, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun getFintechArticles_Success() {
        whenever(articleApi.searchArticles()).thenReturn(Single.just(networkResponse))

        val result = articleRepository.latestFintechArticles().blockingGet()

        Assert.assertEquals(1, result.size)
    }

    @Test
    fun getFintechArticles_Failure() {
        val throwable = Throwable()
        whenever(articleApi.searchArticles()).thenReturn(Single.error(throwable))

        val observer = articleRepository.latestFintechArticles().test()

        observer.assertError(throwable)
    }
}
