package com.awashwinter.testappvideos.utils

sealed class TaskResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : TaskResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : TaskResult<T>(data, message)
}