package com.jay.mvp.v2.contract

import android.content.res.Configuration
import android.os.Bundle

/**
 * @author jaydroid
 * @version 1.0
 * @date 3/2/21
 */
interface IViewLife {

    fun onCreate(savedInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onViewStateRestored(savedInstanceState: Bundle?)

    fun onConfigurationChanged(newConfig: Configuration)

    fun onDestroy()

    fun onStart()

    fun onStop()

    fun onResume()

    fun onPause()

}
