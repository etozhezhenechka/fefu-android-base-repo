package ru.fefu.activitytracker.newactivity.selectiontracker

import androidx.recyclerview.selection.SelectionTracker

class CardPredicate : SelectionTracker.SelectionPredicate<Long>() {
    override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean = nextState

    override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean = nextState

    override fun canSelectMultiple(): Boolean = false
}