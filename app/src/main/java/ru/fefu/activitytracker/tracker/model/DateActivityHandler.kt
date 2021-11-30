package ru.fefu.activitytracker.tracker.model

import ru.fefu.activitytracker.database.Activity
import java.time.LocalDate

class DateActivityHandler {
    private var dateMap: MutableMap<LocalDate, MutableList<Activity>> = mutableMapOf()

    fun addItem(activity: Activity) {
        val localDate = activity.startTime.toLocalDate()

        if (dateMap.containsKey(localDate)) dateMap[localDate]?.add(activity)
        else dateMap[localDate] = mutableListOf(activity)

        sortDateMap()
    }

    fun getCardList(): MutableList<CardItemModel> {
        return if (dateMap.isEmpty()) mutableListOf()
        else {
            generateCardList()
        }
    }

    private fun generateCardList(): MutableList<CardItemModel> {
        val resultList = mutableListOf<CardItemModel>()

        for ((key, value) in dateMap) {
            resultList.add(
                DateLabelModel(
                    FormattedDate(
                        key.dayOfMonth, key.monthValue, key.year
                    ).toString()
                )
            )

            for (item in value) {
                resultList.add(
                    ActivityModel(
                        ActivityInfo(item.distance, item.startTime, item.endTime, item.type)
                    )
                )
            }
        }

        dateMap = mutableMapOf()
        return resultList
    }

    private fun sortDateMap() {
        dateMap = dateMap.toSortedMap()

        for ((_, value) in dateMap) {
            value.sortWith(compareBy { it.startTime })
        }
    }
}