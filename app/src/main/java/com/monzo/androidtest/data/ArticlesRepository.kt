package com.monzo.androidtest.data

import com.monzo.androidtest.data.remote.ArticleService
import com.monzo.androidtest.di.ThreadingModule
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Named

/**
 * Responsible for coordinating paged Article data retrieval from
 * the remote Guardian service for higher layers in the hierarchy.
 */
class ArticleRepository @Inject constructor(
    private val articleApi: ArticleService,
    @Named(ThreadingModule.IO) private val ioScheduler: Scheduler,
    @Named(ThreadingModule.UI) private val uiScheduler: Scheduler
) {
    fun latestFintechArticles(): Single<List<ArticleGroupItem>> {
        return articleApi.searchArticles().map {
            it.response.results.toExternalGroupList()
        }.subscribeOn(ioScheduler).observeOn(uiScheduler)
        // TODO Consider Building the detail view.
        // TODO Replace this whole setup with a Paging flow driven by the DB
    }
}
