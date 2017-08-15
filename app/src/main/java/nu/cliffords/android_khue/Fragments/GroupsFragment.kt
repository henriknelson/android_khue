package nu.cliffords.android_khue.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.khue.Bridge
import nu.cliffords.khue.Group
import nu.cliffords.android_khue.Adapters.GroupsAdapter
import nu.cliffords.android_khue.Interfaces.GroupsAdapterListener
import nu.cliffords.android_khue.R

/**
 * Created by Henrik Nelson on 2017-08-12.
 */

class GroupsFragment : Fragment() {

    var bridge = Bridge("192.168.100.25", "blwK4rKVcav77v4EnDhvxRmsuu2wAoS1-0YqbzpL")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater?.inflate(R.layout.fragment_groups, container, false)

        //Initialize group list view
        val groupListView: RecyclerView? = rootView?.findViewById(R.id.groupListView)
        //Use a linear layout manager
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context)

        groupListView?.layoutManager = layoutManager
        groupListView?.adapter = groupsAdapter
        return rootView
    }

    override fun onStart() {
        super.onStart()
        groupsAdapter.clearGroups()
        bridge.getGroups{ groups ->
            groups.forEach { group ->
                groupsAdapter.addGroup(group)
            }
        }
    }


    private val groupsAdapter: GroupsAdapter = GroupsAdapter(object: GroupsAdapterListener {

        override fun onGroupClick(groupId: String) {
            val lightsFragment = LightsFragment()
            val args = Bundle()
            args.putString("groupId",groupId)
            lightsFragment.arguments = args
            fragmentManager.beginTransaction().replace(R.id.frame_container,lightsFragment).addToBackStack("lights_fragment").commit()
        }

        override fun onToggle(group: Group, state: Boolean) {
            bridge.setGroupState(groupId = group.groupId, on=state,brightness = group.action.bri, transitiontime = 1, listener = {
                Log.i("Bridge","Group attributes set ok!")
            })
        }

        override fun onBrightnessChange(group: Group, brightness: Int) {
            bridge.setGroupState(groupId = group.groupId, brightness = brightness, transitiontime = 1, listener = {

            })
        }
    })


}