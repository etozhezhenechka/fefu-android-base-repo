package ru.fefu.activitytracker

import android.app.Application
import androidx.room.Room
import ru.fefu.activitytracker.database.ActivityDatabase

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    val db: ActivityDatabase by lazy {
        Room.databaseBuilder(
            this,
            ActivityDatabase::class.java,
            "activity_database"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}