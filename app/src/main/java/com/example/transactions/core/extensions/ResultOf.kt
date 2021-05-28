package com.example.transactions.core.extensions

import com.example.transactions.core.domain.RequestFailure
import com.example.transactions.core.domain.ResultOf

inline fun <reified T> ResultOf<T>.doIfFailure(callback: (requestFailure: RequestFailure) -> Unit) {
    if (this is ResultOf.Failure) {
        callback(requestFailure)
    }
}

inline fun <reified T> ResultOf<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is ResultOf.Success) {
        callback(value)
    }
}

inline fun <reified T> ResultOf<T>.doIfInProgress(callback: () -> Unit) {
    if (this is ResultOf.InProgress) {
        callback()
    }
}