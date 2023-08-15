package com.monzo.androidtest.presentation.articlelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.monzo.androidtest.R
import com.monzo.androidtest.data.ArticleListItem
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter for the list of articles in each article group.
 */
class ArticleNestedListAdapter(
    private val context: Context,
    private val articles: List<ArticleListItem>,
    private val onArticleClicked: (apiUrl: String) -> Unit
) : RecyclerView.Adapter<ArticleNestedListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_article_nested_list_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(article: ArticleListItem) {
            val headline = view.findViewById<TextView>(R.id.article_headline)
            val thumbnail = view.findViewById<ImageView>(R.id.article_thumbnail)
            val timestamp = view.findViewById<TextView>(R.id.article_timestamp)

            headline.text = article.headline
            timestamp.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(article.publicationDate)
            Glide.with(context).load(article.thumbnail).circleCrop().into(thumbnail)
            view.setOnClickListener {
                onArticleClicked(article.apiUrl)
            }
        }
    }
}
