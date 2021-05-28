package com.example.transactions.features.home.domain.repository

import com.example.transactions.core.domain.ResultOf
import com.example.transactions.features.home.domain.model.Rate
import com.example.transactions.features.home.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getRates(): Flow<ResultOf<List<Rate>>>

    fun getTransactions(): Flow<ResultOf<List<Transaction>>>
}