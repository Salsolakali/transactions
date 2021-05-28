package com.example.transactions.features.home.domain.usecases

import com.example.transactions.features.home.domain.repository.DataRepository
import javax.inject.Inject

class GetTransactionsUseCase
@Inject constructor(private val repository: DataRepository) {
    fun execute() = repository.getTransactions()
}