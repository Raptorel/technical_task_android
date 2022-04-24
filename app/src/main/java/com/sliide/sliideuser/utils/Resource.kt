package com.sliide.sliideuser.utils

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */
class Resource<T> private constructor(val status: Status, val data: T?, val throwable: Throwable?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> error(error: Throwable? = null): Resource<T> = Resource(Status.ERROR, null, error)
        fun <T> error(): Resource<T> = Resource(Status.ERROR, null, null)
        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data, null)
    }

}