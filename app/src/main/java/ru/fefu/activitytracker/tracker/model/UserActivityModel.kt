package ru.fefu.activitytracker.tracker.model

class UserActivityModel(
    activityProgress: String,
    timeProgress: String,
    activityType: String,
    activityDate: String,
    val user: String
) : ActivityModel(activityProgress, timeProgress, activityType, activityDate)