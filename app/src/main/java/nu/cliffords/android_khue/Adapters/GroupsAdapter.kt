package nu.cliffords.android_khue.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.group_card.view.*
import nu.cliffords.khue.Group
import nu.cliffords.android_khue.Interfaces.GroupsAdapterListener
import nu.cliffords.android_khue.R

/**
 * Created by Henrik Nelson on 2017-08-12.
 */

class GroupsAdapter(val listener: GroupsAdapterListener) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

    private val groupsList: MutableList<Group> = mutableListOf<Group>()

    fun addGroup(group: Group){
        groupsList.add(group)
        notifyDataSetChanged()
    }

    fun clearGroups(){
        groupsList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_card, parent, false)
        return ViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindGroup(groupsList[position])
    }

    override fun getItemCount() = groupsList.count()

    class ViewHolder(val view: View, val listener: GroupsAdapterListener) : RecyclerView.ViewHolder(view) {

        fun bindGroup(group: Group) {
            with(group) {
                view.groupNameView.text = group.name
                view.groupToggleSwitch.isChecked = group.action.on
                view.groupToggleSwitch.setOnCheckedChangeListener{ buttonView, isChecked -> listener.onToggle(group,isChecked)  }
                view.groupSeekBar.progress = group.action.bri
                view.groupSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        group.action.bri = p1
                        listener.onBrightnessChange(group,p1)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                } )
                view.openGroupBtn.setOnClickListener({listener.onGroupClick(group.groupId)})
            }
        }
    }
}