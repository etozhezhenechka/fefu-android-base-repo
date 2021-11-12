package ru.fefu.activitytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [Activity::class], version = 1)
abstract class ActivityDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}