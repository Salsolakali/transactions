package com.example.transactions.core.extensions

import com.example.transactions.features.home.ui.Dictionary

fun toCurrency(currency: String): Dictionary.Currency {
    return when (currency) {
        "CAD" -> Dictionary.Currency.CAD
        "USD" -> Dictionary.Currency.USD
        "AUD" -> Dictionary.Currency.AUD
        else -> Dictionary.Currency.EUR
    }
}