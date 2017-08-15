package nu.cliffords.android_khue.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nu.cliffords.android_khue.R

/**
 * Created by Henrik Nelson on 2017-08-13.
 */

class AboutFragment(): Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_about, container, false)
        return rootView
    }
}