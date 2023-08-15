package com.monzo.androidtest.presentation.articlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.monzo.androidtest.data.ArticleGroupItem
import com.monzo.androidtest.data.ArticleRepository
import com.monzo.androidtest.util.BaseViewModel
import com.monzo.androidtest.util.plusAssign
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : BaseViewModel() {

    private val _state = MutableLiveData<ArticleListStates>()
    val state: LiveData<ArticleListStates> = _state

    fun onViewCreated() {
        refreshArticles()
    }

    private fun loadLatestFintechArticles() {
        articleRepository.latestFintechArticles()
            .subscribe({ articleGroups ->
                _state.value = ArticleListStates.Refreshing(isRefreshing = false)
                _state.value = ArticleListStates.Success(articleGroupsList = articleGroups)
            }, {
                _state.value = ArticleListStates.Error(it)
                if (it is IOException) {
                    _state.value = ArticleListStates.Refreshing(isRefreshing = false)
                }
            }).also { disposables += it }
    }

    fun refreshArticles() {
        _state.value = ArticleListStates.Refreshing(isRefreshing = true)
        loadLatestFintechArticles()
    }
}

sealed class ArticleListStates {
    data class Success(val articleGroupsList: List<ArticleGroupItem>) : ArticleListStates()
    data class Refreshing(val isRefreshing: Boolean = true) : ArticleListStates()
    data class Error(val error: Throwable) : ArticleListStates()
}
