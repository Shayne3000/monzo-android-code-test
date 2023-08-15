package com.monzo.androidtest.presentation.articlelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monzo.androidtest.R
import com.monzo.androidtest.data.ArticleGroupItem
import java.util.ArrayList
import java.util.Calendar

/**
 * Adapter for the list of article groups which nests an internal list for each article group.
 */
class ArticleGroupListAdapter(
    private val context: Context,
    private val onArticleInGroupClicked: (apiUrl: String) -> Unit
) : RecyclerView.Adapter<ArticleGroupListAdapter.ArticleGroupViewHolder>() {
    private var articleGroups: MutableList<ArticleGroupItem> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleGroupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_article_group_item, parent, false)
        return ArticleGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleGroupViewHolder, position: Int) {
        holder.bind(articleGroups[position])
    }

    override fun getItemCount(): Int {
        return articleGroups.size
    }

    fun addArticleGroups(articleGroups: List<ArticleGroupItem>) {
        this.articleGroups.addAll(articleGroups)
        notifyItemRangeInserted(0, itemCount)
    }

    fun clearArticleGroups() {
        val tempItemCount = itemCount
        this.articleGroups.clear()
        notifyItemRangeRemoved(0, tempItemCount)
    }

    inner class ArticleGroupViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(articleGroup: ArticleGroupItem) {
            val groupTitle = view.findViewById<TextView>(R.id.articles_group_title)
            val groupList = view.findViewById<RecyclerView>(R.id.articles_nested_list)

            val currentWeek = Calendar.getInstance()[Calendar.WEEK_OF_YEAR]

            when (val truePublishedWeek = currentWeek - articleGroup.publishedWeek) {
                0 -> {
                    groupTitle.text = context.getString(R.string.article_group_header_this_week)
                }

                1 -> {
                    groupTitle.text = context.getString(R.string.article_group_header_last_week)
                }

                else -> {
                    groupTitle.text = context.getString(
                        R.string.article_group_header_past_weeks,
                        "$truePublishedWeek"
                    )
                }
            }
            val linearLayoutManager = object : LinearLayoutManager(view.context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            val nestedArticleAdapter =
                ArticleNestedListAdapter(context, articleGroup.nestedArticleList) {
                    onArticleInGroupClicked(it)
                }
            groupList.layoutManager = linearLayoutManager
            groupList.adapter = nestedArticleAdapter
        }

    }
}
