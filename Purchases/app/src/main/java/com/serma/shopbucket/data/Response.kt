package com.serma.shopbucket.data

sealed class Response<T>
data class Success<T>(val result: T) : Response<T>()
data class Failure<T>(val error: Throwable) : Response<T>()