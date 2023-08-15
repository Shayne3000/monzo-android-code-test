package com.monzo.androidtest.articles

import com.monzo.androidtest.util.RxSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.monzo.androidtest.data.ArticleRepository
import com.monzo.androidtest.presentation.articlelist.ArticleListViewModel
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ArticleListViewModelTest {
    @Rule @JvmField val rxSchedulerRule = RxSchedulerRule()
    @Rule @JvmField val rule = InstantTaskExecutorRule()

    private val repository = mock<ArticleRepository>()

    private val viewModel get() = ArticleListViewModel(repository)

    @Test
    fun test() {
        whenever(repository.latestFintechArticles()).thenReturn(Single.just(emptyList()))

        Assert.assertNotNull(viewModel.state.value)
    }
}
