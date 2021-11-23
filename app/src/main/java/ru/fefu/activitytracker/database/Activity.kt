package ru.fefu.activitytracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.fefu.activitytracker.newactivity.model.ActivityType
import java.time.LocalDateTime

@Entity
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "type") val type: ActivityType,
    @ColumnInfo(name = "start_time") val startTime: LocalDateTime,
    @ColumnInfo(name = "end_time") val endTime: LocalDateTime,
    @ColumnInfo(name = "coordinate_list") val coordinateList: List<Pair<Double, Double>>,
    @ColumnInfo(name = "is_finished") val isFinished: Boolean = false
)