package com.monzo.androidtest.presentation.articlelist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.ArticleAdapter
import com.monzo.androidtest.databinding.ActivityArticleListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleListBinding
    private lateinit var adapter: ArticleGroupListAdapter
    private val vm: ArticleListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.articleListToolbar.title = getString(R.string.article_list_title)
        binding.articleListToolbar.setTitleTextColor(getColor(R.color.off_white))
        setSupportActionBar(binding.articleListToolbar)

        adapter = ArticleGroupListAdapter(this) {
            // TODO start the detail activity passing in the apiUrl of the
            //  clicked article as an intent extra for loading in the detail screen.
        }
        binding.articlesGroupList.layoutManager = LinearLayoutManager(this)
        binding.articlesGroupList.adapter = adapter

        binding.articlesSwiperefreshlayout.setOnRefreshListener { vm.refreshArticles() }

        vm.onViewCreated()

        vm.state.observe(this, this::handleArticleListState)
    }

    private fun handleArticleListState(state: ArticleListStates) {
        when (state) {
            is ArticleListStates.Refreshing -> {
                binding.articlesSwiperefreshlayout.isRefreshing = state.isRefreshing
                if (binding.articlesSwiperefreshlayout.isRefreshing) {
                    adapter.clearArticleGroups()
                }
            }

            is ArticleListStates.Success -> {
                adapter.addArticleGroups(state.articleGroupsList)
            }

            is ArticleListStates.Error -> {
                val errorMessage = state.error.message ?: getString(R.string.generic_error_message)
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
