package nu.cliffords.android_khue.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.khue.Light
import nu.cliffords.android_khue.Adapters.LightsAdapter
import nu.cliffords.android_khue.Interfaces.LightsAdapterListener
import nu.cliffords.android_khue.R
import nu.cliffords.khue.Bridge

/**
 * Created by Henrik Nelson on 2017-08-12.
 */

class LightsFragment : Fragment() {

    private var bridge = Bridge("192.168.100.25", "blwK4rKVcav77v4EnDhvxRmsuu2wAoS1-0YqbzpL")
    private var groupId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        groupId = arguments.getString("groupId")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_lights, container, false)
        //Initialize group list view
        val lightsListView: RecyclerView? = rootView?.findViewById(R.id.lightListView)
        //Use a linear layout manager
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context)

        lightsListView?.layoutManager = layoutManager
        lightsListView?.adapter = lightsAdapter
        return rootView
    }

    override fun onStart() {
        super.onStart()
        lightsAdapter.clearLights()
        bridge.getGroup(groupId,{ group ->

            group.lights.forEach { lightId ->
                bridge.getLight(lightId,{ light ->
                    lightsAdapter.addLight(light)
                })
            }
        })
    }

       private val lightsAdapter: LightsAdapter = LightsAdapter(object: LightsAdapterListener {
        override fun onToggle(light: Light, state: Boolean) {
            bridge.setLightState(lightId = light.lightId, on=state,brightness = light.state.bri, transitiontime = 1, listener = { json -> })
        }

        override fun onBrighnessChange(light: Light, brightness: Int) {
            bridge.setLightState(lightId = light.lightId, brightness = brightness, transitiontime = 1, listener = { json -> })
        }
    })


}