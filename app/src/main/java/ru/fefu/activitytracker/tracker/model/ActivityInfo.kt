package ru.fefu.activitytracker.tracker.model

import ru.fefu.activitytracker.newactivity.model.ActivityType
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.min

class ActivityInfo(
    private val id: Int, private val distance: Double, private val startTime: LocalDateTime,
    private val endTime: LocalDateTime, private val type: ActivityType,
    private val username: String = ""
) {

    private fun getDurationString(duration: Duration, hoursOnly: Boolean = false): String {
        val hours = duration.toHours()
        val minutes = (duration.seconds % (60 * 60) / 60).toInt()

        return if (!hoursOnly) "$hours ч. $minutes м."
        else "$hours ч."
    }

    private fun getTimeString(dateTime: LocalDateTime): String {
        val hourStr = if (dateTime.hour < 9) "0${dateTime.hour}" else "${dateTime.hour}"
        val minuteStr = if (dateTime.minute < 9) "0${dateTime.minute}" else "${dateTime.minute}"

        return "${hourStr}:${minuteStr}"
    }

    fun getId(): Int = id

    fun getDistance(): String {
        return if (distance >= 1000) "%.2f км.".format(distance / 1000)
        else "%.2f м.".format(distance)
    }

    fun getStartTime(): String = getTimeString(startTime)

    fun getEndTime(): String = getTimeString(endTime)

    fun getDuration(): String {
        val duration = Duration.between(endTime, startTime).abs()
        return getDurationString(duration)
    }

    fun getOffset(): String {
        val currentDate = LocalDateTime.now()

        val offset = Duration.between(currentDate, endTime).abs()
        return "${getDurationString(offset, true)} назад"
    }

    fun getType(): String = when (type) {
        ActivityType.BIKING -> "Велосипед"
        ActivityType.RUNNING -> "Бег"
        ActivityType.SWIMMING -> "Плавание"
    }

    fun getUsername(): String = username
}