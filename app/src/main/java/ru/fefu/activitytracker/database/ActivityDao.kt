package ru.fefu.activitytracker.database

import android.content.IntentSender
import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity WHERE is_finished=1")
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

    @Query("UPDATE activity SET is_finished=:isFinished WHERE id=:id")
    fun updateIsFinishedById(id: Int, isFinished: Boolean)

    @Query("UPDATE activity SET distance=:distance WHERE id=:id")
    fun updateDistanceById(id: Int, distance: Double)

    @Query("UPDATE activity SET start_time=:startTime WHERE id=:id")
    fun updateStartTimeById(id: Int, startTime: LocalDateTime)

    @Query("UPDATE activity SET end_time=:endTime WHERE id=:id")
    fun updateEndTimeById(id: Int, endTime: LocalDateTime)

    @Delete
    fun delete(activity: Activity)
}