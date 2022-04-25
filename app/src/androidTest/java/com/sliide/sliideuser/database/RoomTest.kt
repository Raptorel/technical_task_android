package com.sliide.sliideuser.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Robert Ruxandrescu on 4/25/22.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UsersDatabase

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UsersDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertUserAndGetById() = runBlockingTest {
        // GIVEN - Insert a user.
        val user = DatabaseUser(1, "Robert", "rob@android.com", "Today")
        database.usersDao.insertUser(user)

        // WHEN - Get the user by id from the database.
        val loaded = database.usersDao.getUserById(user.id)

        // THEN - The loaded data contains the expected values.
        ViewMatchers.assertThat(loaded as DatabaseUser, (not(nullValue())) as Matcher<DatabaseUser>)
        ViewMatchers.assertThat(loaded.id, `is`(user.id))
        ViewMatchers.assertThat(loaded.name, `is`(user.name))
        ViewMatchers.assertThat(loaded.email, `is`(user.email))
        ViewMatchers.assertThat(loaded.creationTime, `is`(user.creationTime))
    }
}