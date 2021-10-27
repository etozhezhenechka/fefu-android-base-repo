package ru.fefu.activitytracker.tracker.model

import java.time.Duration
import java.time.LocalTime

class ActivityInfo(private val distance: Double, private val startTime: LocalTime,
                   private val endTime: LocalTime, private val type: String,
                   private val username: String = "") {

    private fun getDurationString(duration: Duration, hoursOnly: Boolean = false): String {
        val hours = duration.toHours()
        val minutes = (duration.seconds % (60 * 60) / 60).toInt()

        return if (!hoursOnly) "$hours ч. $minutes м."
        else "$hours ч."
    }

    fun getDistance(): String = "$distance км."

    fun getDuration(): String {
        val duration = Duration.between(endTime, startTime).abs()
        return getDurationString(duration)
    }

    fun getOffset(): String {
        val currentDate = LocalTime.now()

        val offset = Duration.between(currentDate, endTime).abs()
        return "${getDurationString(offset, true)} назад"
    }

    fun getType(): String = type

    fun getUsername(): String = username
}