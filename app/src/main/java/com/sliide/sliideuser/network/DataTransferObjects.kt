package com.sliide.sliideuser.network

import com.sliide.sliideuser.database.DatabaseUser
import com.sliide.sliideuser.domain.User
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */

const val DATE_PATTERN = "yyyy-MM-dd"

@JsonClass(generateAdapter = true)
data class NetworkUser(
    val id: Long = Random().nextLong(),
    val name: String,
    val email: String,
    val gender: String = "female",
    val status: String = "inactive"
)

/**
 * Extension methods for easy conversion from one form to another
 */
fun List<NetworkUser>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.id,
            name = it.name,
            email = it.email,
            creationTime = SimpleDateFormat(DATE_PATTERN).format(Date())
        )
    }
}

fun List<NetworkUser>.asDatabaseModel(): Array<DatabaseUser> {
    return map {
        DatabaseUser(
            id = it.id,
            name = it.name,
            email = it.email,
            creationTime = SimpleDateFormat(DATE_PATTERN).format(Date())
        )
    }.toTypedArray()
}

fun NetworkUser.asDatabaseModel(): DatabaseUser {
    return DatabaseUser(
        id = this.id,
        name = this.name,
        email = this.email,
        creationTime = SimpleDateFormat(DATE_PATTERN).format(Date())
    )
}