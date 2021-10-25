package ru.fefu.activitytracker.tracker.model

open class ActivityModel(
    val activityProgress: String,
    val timeProgress: String,
    val activityType: String,
    val activityDate: String
) : CardItemModel