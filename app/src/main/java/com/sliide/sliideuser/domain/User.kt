package com.sliide.sliideuser.domain

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val creationTime: String
)
