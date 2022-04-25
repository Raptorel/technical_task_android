package com.sliide.sliideuser.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */
@Database(entities = [DatabaseUser::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}

@Dao
interface UsersDao {

    @Query("select * from databaseuser")
    fun getUsers(): LiveData<List<DatabaseUser>>

    //OnConflictStrategy.IGNORE because we don't want to overwrite the user date
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: DatabaseUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DatabaseUser)

    @Query("DELETE FROM databaseuser WHERE id = :userId")
    fun deleteUser(userId: Long)

    @Query("DELETE FROM databaseuser")
    fun deleteAll()
}

private lateinit var INSTANCE: UsersDatabase

fun getDatabase(context: Context): UsersDatabase {
    synchronized(UsersDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java,
                "videos").build()
        }
        return INSTANCE
    }
}