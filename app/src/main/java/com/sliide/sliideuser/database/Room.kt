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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: DatabaseUser)

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