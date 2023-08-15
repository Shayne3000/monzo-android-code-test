package com.monzo.androidtest.presentation.articlelist

import com.monzo.androidtest.util.RxSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.monzo.androidtest.data.ArticleGroupItem
import com.monzo.androidtest.data.ArticleRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class ArticleListViewModelTests {
    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository: ArticleRepository = mock()
    private val articleGroups: List<ArticleGroupItem> = mock()
    private lateinit var vm: ArticleListViewModel

    @Before
    fun setup() {
        vm = ArticleListViewModel(repository)
    }

    @Test
    fun latestFintechArticles_ReturnsEmptyList_OnSuccess() {
        whenever(repository.latestFintechArticles()).thenReturn(Single.just(emptyList()))

        vm.onViewCreated()

        assertEquals(
            ArticleListStates.Success(emptyList()),
            vm.state.value
        )
    }

    @Test
    fun latestFintechArticles_ReturnsArticleGroups_onSuccess() {
        whenever(repository.latestFintechArticles()).thenReturn(Single.just(articleGroups))

        vm.refreshArticles()

        assertEquals(
            ArticleListStates.Success(articleGroups),
            vm.state.value
        )
    }

    @Test(expected = Exception::class)
    fun latestFintechArticles_ReturnsError_OnFailure() {
        val exception = Exception()
        whenever(repository.latestFintechArticles()).thenThrow(exception)

        vm.refreshArticles()

        assertEquals(
            ArticleListStates.Error(exception),
            vm.state.value
        )
    }
}
