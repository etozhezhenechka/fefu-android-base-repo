package ru.fefu.activitytracker.tracker.model

class UsersActivityModel(id: Int,activityInfo: ActivityInfo) : ActivityModel(id, activityInfo) {
    val user = activityInfo.getUsername()
}