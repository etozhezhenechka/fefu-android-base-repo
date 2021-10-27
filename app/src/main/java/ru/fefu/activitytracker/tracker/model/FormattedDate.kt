package ru.fefu.activitytracker.tracker.model

import android.text.format.DateUtils
import java.time.LocalDate
import java.time.ZoneOffset

class FormattedDate(day: Int, month: Int, year: Int) {
    private val date = LocalDate.of(year, month, day)

    private val monthMap = mapOf(
        1 to "Январь", 2 to "Февраль", 3 to "Март", 4 to "Апрель",
        5 to "Май", 6 to "Июнь", 7 to "Июль", 8 to "Август",
        9 to "Сентябрь", 10 to "Октябрь", 11 to "Ноябрь", 12 to "Декабрь"
    )

    override fun toString(): String {
        val millisecondsTime = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        return when {
            DateUtils.isToday(millisecondsTime) -> "Сегодня"
            DateUtils.isToday(millisecondsTime + DateUtils.DAY_IN_MILLIS) -> "Вчера"
            DateUtils.isToday(millisecondsTime + 2 * DateUtils.DAY_IN_MILLIS) -> "Позавчера"
            else -> {
                monthMap[date.monthValue] + " " + date.year + " года"
            }
        }
    }
}