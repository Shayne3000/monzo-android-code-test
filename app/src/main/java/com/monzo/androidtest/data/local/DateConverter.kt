package com.monzo.androidtest.data.local

import androidx.room.TypeConverter
import java.util.Date

/**
 * Class responsible for converting [Date] to [Long]
 * for persistence in the DB and vice versa on retrieval.
 */
class DateConverter {
    @TypeConverter
    fun timestampToDate(value: Long) = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time
}
