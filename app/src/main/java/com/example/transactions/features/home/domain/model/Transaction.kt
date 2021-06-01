package com.example.transactions.features.home.domain.model

data class Transaction(var sku: String, var amount: Double, var currency: String) {
    constructor() : this("", 0.0, "")
}