package ru.fefu.activitytracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

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

    @Update
    fun update(activity: Activity)

    @Update
    fun updateAll(vararg activity: Activity)

    @Delete
    fun delete(activity: Activity)
}