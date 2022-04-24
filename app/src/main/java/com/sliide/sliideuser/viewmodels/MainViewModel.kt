package com.sliide.sliideuser.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sliide.sliideuser.repositories.MainRepository
import com.sliide.sliideuser.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    application: Application
): AndroidViewModel(application) {

    val users = mainRepository.usersLiveData
    private val _progress = mainRepository.progressLiveData
    val progress: LiveData<Resource<Unit>> = _progress

    init {
        viewModelScope.launch {
            try {
                mainRepository.refreshUsers()
            } catch (e: Exception) {
                _progress.value = Resource.error()
            }
        }
    }

}