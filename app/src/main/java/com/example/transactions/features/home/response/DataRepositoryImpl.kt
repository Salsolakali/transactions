package com.example.transactions.features.home.response

import com.example.transactions.core.data.APIService
import com.example.transactions.core.data.FailureFactory
import com.example.transactions.core.domain.ResultOf
import com.example.transactions.core.extensions.safeCall
import com.example.transactions.features.home.domain.model.Rate
import com.example.transactions.features.home.domain.model.Transaction
import com.example.transactions.features.home.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DataRepositoryImpl(private val apiService: APIService) : DataRepository {
    override fun getRates(): Flow<ResultOf<List<Rate>>> = flow {
        emit(apiService.getRates().safeCall({ response -> response.map { it.toDomain() } }))
    }.catch {
        emit(FailureFactory().handleException(it))
    }

    override fun getTransactions(): Flow<ResultOf<List<Transaction>>> = flow {
        emit(apiService.getTransactions().safeCall({ response ->
            response.map { it.toDomain() }
        }))
    }.catch {
        emit(FailureFactory().handleException(it))
    }
}
