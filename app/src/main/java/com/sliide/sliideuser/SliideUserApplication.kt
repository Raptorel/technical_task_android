package com.sliide.sliideuser

import android.app.Application
import com.sliide.sliideuser.di.DaggerAppComponent

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
class SliideUserApplication: Application() {
    companion object {
        val appComponent by lazy {
            DaggerAppComponent.builder().build()
        }
    }
}