package com.sliide.sliideuser.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sliide.sliideuser.repositories.MainRepository
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}