package nu.cliffords.android_khue.Interfaces

import nu.cliffords.khue.Group

/**
 * Created by Henrik Nelson on 2017-08-11.
 */
interface GroupsAdapterListener {
    fun onToggle(group: Group, state: Boolean)
    fun onBrightnessChange(group: Group, brightness: Int)
    fun onGroupClick(groupId: String)
}