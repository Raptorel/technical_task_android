package com.sliide.sliideuser.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sliide.sliideuser.domain.User
import com.sliide.sliideuser.network.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */
@Entity
data class DatabaseUser constructor(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val email: String?,
    val creationTime: String
)

fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.id,
            name = it.name,
            email = it.email,
            creationTime = SimpleDateFormat(DATE_PATTERN).format(Date())
        )
    }
}