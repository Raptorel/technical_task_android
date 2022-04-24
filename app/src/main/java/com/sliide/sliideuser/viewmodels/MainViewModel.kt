package com.sliide.sliideuser.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.sliideuser.repositories.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    application: Application
): AndroidViewModel(application) {

    val users = mainRepository.users

    init {
        viewModelScope.launch {
            mainRepository.refreshUsers()
        }
    }

}