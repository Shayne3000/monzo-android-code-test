package com.monzo.androidtest.presentation.articledetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.monzo.androidtest.databinding.ActivityArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
