package com.sliide.sliideuser.di

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
}