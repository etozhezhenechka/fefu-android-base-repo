package ru.fefu.activitytracker.tracker.model

open class ActivityModel(id: Int, activityInfo: ActivityInfo) : CardItemModel(id) {
    val activityProgress = activityInfo.getDistance()
    val timeProgress = activityInfo.getDuration()
    val activityType = activityInfo.getType()
    val activityDate = activityInfo.getOffset()
}