package com.monzo.androidtest.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_TO_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // since there is no DB schema change, do nothing for now.
        // TODO Add a migration if and when the article "favouriting" feature is added to the DB.
    }
}
