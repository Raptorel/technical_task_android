package com.sliide.sliideuser.di

import com.sliide.sliideuser.screens.MainFragment
import dagger.Component

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}