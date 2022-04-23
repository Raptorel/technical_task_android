package com.sliide.sliideuser.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sliide.sliideuser.repositories.MainRepository
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val mainRepository: MainRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepository, application) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}