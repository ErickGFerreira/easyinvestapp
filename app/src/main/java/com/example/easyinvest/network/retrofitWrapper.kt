package com.example.easyinvest.network

import com.example.easyinvest.network.model.Error
import com.example.easyinvest.network.model.Result
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

suspend fun <T> retrofitWrapper(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (ioException: IOException) {
        Result.failure(getNetworkError())
    }
    catch (httpException: HttpException){
       Result.failure(getGenericError())
    }

}

private fun getNetworkError(): Error {
    return Error(
        code = HttpURLConnection.HTTP_INTERNAL_ERROR,
        title = NetworkConstants.CONNECTION_ERROR_TITLE,
        message = NetworkConstants.CONNECTION_ERROR_MESSAGE
    )
}

private fun getGenericError(): Error {
    return Error(
        code = HttpURLConnection.HTTP_BAD_REQUEST,
        title = NetworkConstants.GENERIC_NETWORK_ERROR_TITLE,
        message = NetworkConstants.GENERIC_NETWORK_ERROR_MESSAGE
    )
}