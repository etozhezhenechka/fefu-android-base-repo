package ru.fefu.activitytracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity")
    fun getAll(): LiveData<List<Activity>>

    @Query("SELECT * FROM activity WHERE id=:id")
    fun getById(id: Int): Activity

    @Insert
    fun insert(activity: Activity)

    @Insert
    fun insertAll(vararg activity: Activity)

    @Delete
    fun delete(activity: Activity)
}