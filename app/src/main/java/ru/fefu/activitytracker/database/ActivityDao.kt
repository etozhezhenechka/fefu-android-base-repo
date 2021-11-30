package ru.fefu.activitytracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity")
    fun getAll(): LiveData<List<Activity>>

    @Query("SELECT * FROM activity WHERE id=:id")
    fun getById(id: Int): Activity

    @Query("SELECT * FROM activity WHERE id=:id LIMIT 1")
    fun getByIdLiveData(id: Int): LiveData<Activity>

    @Insert
    fun insert(activity: Activity): Long

    @Insert
    fun insertAll(vararg activity: Activity)

    @Update
    fun update(activity: Activity)

    @Update
    fun updateAll(vararg activity: Activity)

    @Query("UPDATE activity SET coordinate_list=:coordinateList WHERE id=:id")
    fun updateCoordinatesById(id: Int, coordinateList: List<Pair<Double, Double>>)

    @Delete
    fun delete(activity: Activity)
}