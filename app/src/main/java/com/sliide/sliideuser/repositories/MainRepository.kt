package com.sliide.sliideuser.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sliide.sliideuser.database.UsersDatabase
import com.sliide.sliideuser.database.asDomainModel
import com.sliide.sliideuser.domain.User
import com.sliide.sliideuser.network.GoRestService
import com.sliide.sliideuser.network.NetworkUser
import com.sliide.sliideuser.network.asDatabaseModel
import com.sliide.sliideuser.utils.Resource
import com.sliide.sliideuser.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
class MainRepository @Inject constructor(
    private val goRestService: GoRestService,
    private val database: UsersDatabase
) {

    val progressLiveData: MutableLiveData<Resource<Unit>> = SingleLiveEvent()
    val usersLiveData: LiveData<List<User>> = Transformations.map(database.usersDao.getUsers()) {
        it.asDomainModel()
    }

    suspend fun refreshUsers() {
        progressLiveData.value = Resource.loading()
        withContext(Dispatchers.IO) {
            val networkUsers = goRestService.getUsersAsync().await()
            database.usersDao.deleteAll()
            database.usersDao.insertAll(*networkUsers.asDatabaseModel())
            progressLiveData.postValue(Resource.success(Unit))
        }
    }

    suspend fun deleteUser(userId: Long) {
        progressLiveData.value = Resource.loading()
        withContext(Dispatchers.IO) {
            val response = goRestService.deleteUserAsync(userId).await()
            if (response.isSuccessful) {
                database.usersDao.deleteUser(userId)
                progressLiveData.postValue(Resource.success(Unit))
            } else {
                progressLiveData.postValue(Resource.error())
            }
        }
    }

    suspend fun addUser(networkUser: NetworkUser) {
        progressLiveData.value = Resource.loading()
        withContext(Dispatchers.IO) {
            val response = goRestService.addUserAsync(networkUser).await()
            if (response.isSuccessful) {
                database.usersDao.insertUser(networkUser.asDatabaseModel())
                progressLiveData.postValue(Resource.success(Unit))
            } else {
                progressLiveData.postValue(Resource.error())
            }
        }
    }
}