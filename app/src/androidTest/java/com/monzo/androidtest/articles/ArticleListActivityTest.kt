package com.monzo.androidtest.articles

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.monzo.androidtest.presentation.articlelist.ArticleListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleListActivityTest {
    @get:Rule
    val testRule = ActivityScenarioRule(ArticleListActivity::class.java)

    @Test
    fun test() {
        val scenario = testRule.scenario
        scenario.moveToState(Lifecycle.State.CREATED)
    }
}
