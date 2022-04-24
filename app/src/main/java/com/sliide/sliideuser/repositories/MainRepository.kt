package com.sliide.sliideuser.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sliide.sliideuser.database.UsersDatabase
import com.sliide.sliideuser.database.asDomainModel
import com.sliide.sliideuser.domain.User
import com.sliide.sliideuser.network.GoRestService
import com.sliide.sliideuser.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
class MainRepository @Inject constructor(
    private val goRestService: GoRestService,
    private val database: UsersDatabase) {

    val users: LiveData<List<User>> = Transformations.map(database.usersDao.getUsers()) {
        it.asDomainModel()
    }

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val networkUsers = goRestService.getUsers().await()
            database.usersDao.insertAll(*networkUsers.asDatabaseModel())
        }
    }
}