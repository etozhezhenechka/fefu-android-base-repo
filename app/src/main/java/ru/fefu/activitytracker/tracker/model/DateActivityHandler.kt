package ru.fefu.activitytracker.tracker.model

import ru.fefu.activitytracker.database.Activity
import java.time.LocalDate

class DateActivityHandler {
    private var dateMap: MutableMap<LocalDate, MutableList<Activity>> = mutableMapOf()
    var activityItemCount: Int = 0

    fun addItem(activity: Activity) {
        val localDate = activity.startTime.toLocalDate()

        if (dateMap.containsKey(localDate)) dateMap[localDate]?.add(activity)
        else dateMap[localDate] = mutableListOf(activity)

        sortDateMap()
    }

    fun removeItem(activity: Activity) {
        val localDate = activity.startTime.toLocalDate()

        if (!dateMap.containsKey(localDate)) return
        else {
            dateMap[localDate]?.remove(activity)
            if (dateMap[localDate]?.isEmpty() == true) {
                dateMap.remove(localDate)
            }
        }
    }

    fun getModelList(): MutableList<CardItemModel> {
        return if (dateMap.isEmpty()) mutableListOf()
        else {
            makeActivitiesList()
        }
    }

    private fun makeActivitiesList(): MutableList<CardItemModel> {
        val resultList = mutableListOf<CardItemModel>()
        activityItemCount = 0

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
                        item.id,
                        ActivityInfo(14.8, item.startTime, item.endTime, item.type)
                    )
                )
                ++activityItemCount
            }
        }

        return resultList
    }

    private fun sortDateMap() {
        dateMap = dateMap.toSortedMap()

        for ((_, value) in dateMap) {
            value.sortWith(compareBy { it.startTime })
        }
    }
}