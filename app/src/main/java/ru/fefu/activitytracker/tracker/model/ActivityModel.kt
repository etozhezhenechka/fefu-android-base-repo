package ru.fefu.activitytracker.tracker.model

open class ActivityModel(activityInfo: ActivityInfo) : CardItemModel {
    val activityProgress = activityInfo.getDistance()
    val timeProgress = activityInfo.getDuration()
    val activityType = activityInfo.getType()
    val activityDate = activityInfo.getOffset()
}