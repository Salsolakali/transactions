package com.example.transactions.core.domain

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    data class Failure(val requestFailure: RequestFailure): ResultOf<Nothing>()
    object InProgress : ResultOf<Nothing>()
}