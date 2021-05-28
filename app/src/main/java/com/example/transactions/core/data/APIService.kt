package com.example.transactions.core.data

import com.example.transactions.features.home.response.RateApi
import com.example.transactions.features.home.response.TransactionApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface APIService {

    @Headers("Accept: application/json")
    @GET("rates")
    suspend fun getRates(): Response<List<RateApi>>

    @Headers("Accept: application/json")
    @GET("transactions")
    suspend fun getTransactions(): Response<List<TransactionApi>>
}