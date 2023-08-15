package com.monzo.androidtest.api.model

import com.monzo.androidtest.data.ApiArticle

data class ApiArticleListResponse(val response: ApiArticleList)

data class ApiArticleList(val results: List<ApiArticle>)
