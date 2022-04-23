package com.sliide.sliideuser.di

import android.app.Application
import com.sliide.sliideuser.SliideUserApplication
import com.sliide.sliideuser.services.Network
import dagger.Module
import dagger.Provides

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
@Module
class AppModule {
    @Provides
    fun provideGoRestService() = Network.goRestService

    @Provides
    fun provideApplication(): Application = SliideUserApplication.INSTANCE
}