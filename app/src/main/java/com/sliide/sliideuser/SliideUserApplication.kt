package com.sliide.sliideuser

import android.app.Application
import com.sliide.sliideuser.di.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
class SliideUserApplication @Inject constructor(): Application() {
    companion object {
        val appComponent by lazy {
            DaggerAppComponent.builder().build()
        }
        lateinit var INSTANCE: SliideUserApplication
    }

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
    }
}