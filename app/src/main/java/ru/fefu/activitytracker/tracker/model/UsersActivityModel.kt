package ru.fefu.activitytracker.tracker.model

class UsersActivityModel(activityInfo: ActivityInfo) : ActivityModel(activityInfo) {
    val user = activityInfo.getUsername()
}