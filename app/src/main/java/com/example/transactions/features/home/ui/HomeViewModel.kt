package com.example.transactions.features.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transactions.core.domain.ResultOf
import com.example.transactions.features.home.domain.model.Rate
import com.example.transactions.features.home.domain.model.Transaction
import com.example.transactions.features.home.domain.usecases.GetRatesUseCase
import com.example.transactions.features.home.domain.usecases.GetTransactionsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val getRatesUseCase: GetRatesUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {
    private lateinit var job: Job
    var rates: MutableLiveData<ResultOf<List<Rate>>> = MutableLiveData()
    var transactions: MutableLiveData<ResultOf<List<Transaction>>> = MutableLiveData()

    var currencies = mutableListOf<String>()

    fun getRates() {
        job = viewModelScope.launch {
            getRatesUseCase.execute()
                .onStart {
                    emit(ResultOf.InProgress)
                }
                .collect {
                    rates.postValue(it)
                }
        }
    }

    fun getTransactions() {
        job = viewModelScope.launch {
            getTransactionsUseCase.execute()
                .onStart { emit(ResultOf.InProgress) }
                .collect {
                    transactions.postValue(it)
                }
        }
    }

    fun mapResults(rates: List<Transaction>) {
        val mutableList = mutableListOf<String>()
        rates.forEach {
            if(!mutableList.contains(it.sku)){
                mutableList.add(it.sku)
            }
        }
        mutableList
    }
}