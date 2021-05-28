package com.example.transactions.features.home.response

import com.example.transactions.features.home.domain.model.Transaction

data class TransactionApi(
    val sku: String,
    val amount: String,
    val currency: String
){
    fun toDomain(): Transaction{
        return Transaction(
            sku = sku,
            amount = amount.toDouble(),
            currency = currency
        )
    }
}
