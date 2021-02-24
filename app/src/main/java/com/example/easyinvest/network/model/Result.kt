package com.example.easyinvest.network.model


class Result<T> private constructor(
    private val success: T?,
    private val failure: Error?
) {

    suspend fun onSuccess(action: suspend (T) -> Unit): Result<T> {
        success?.let { action(it) }
        return this
    }

    suspend fun onFailure(action: suspend (Error) -> Unit): Result<T> {
        failure?.let { action(it) }
        return this
    }

    suspend fun onAny(action: suspend (Result<T>) -> Unit): Result<T> {
        success?.let { action(this) }
        failure?.let { action(this) }
        return this
    }

    companion object {
        fun <T> success(data: T) = Result(
            success = data,
            failure = null
        )
        fun <T> failure(error: Error) =
            Result<T>(
                success = null,
                failure = error
            )
    }

}