package com.example.transactions.core.data

import com.example.transactions.core.domain.RequestFailure
import com.example.transactions.core.domain.ResultOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class FailureFactory {
    open fun handleCode(code: Int, errorBody: ResponseBody?) =
        ResultOf.Failure(
            requestFailure = when (code) {
                400 -> createApiError(errorBody)
                else -> RequestFailure.ApiError()
            }
        )

    open fun handleException(exception: Throwable) =
        ResultOf.Failure(
            requestFailure = when (exception) {
                is UnknownHostException, is SocketTimeoutException -> RequestFailure.NoConnectionError
                else -> RequestFailure.UnknownError
            }
        )

    private fun createApiError(responseBody: ResponseBody?): RequestFailure.ApiError {
        val newLine = "\n"
        try {
            responseBody?.let {
                val moshi = Moshi.Builder().build()
                val listMyData =
                    Types.newParameterizedType(List::class.java, ErrorResponse::class.java)
                val adapter: JsonAdapter<List<ErrorResponse>> = moshi.adapter(listMyData)
                val serverErrorList = adapter.fromJson(it.string())
                val stringBuffer = StringBuilder()
                val codeList = mutableListOf<Int>()
                serverErrorList?.forEach { serverError ->
                    stringBuffer.append(serverError.message).append(newLine)
                    codeList.add(serverError.code)
                }
                return RequestFailure.ApiError(
                    code = codeList,
                    message = stringBuffer.toString().substringBeforeLast(newLine)
                )
            }
            return RequestFailure.ApiError()
        } catch (exception: Exception) {
            return RequestFailure.ApiError()
        }
    }
}