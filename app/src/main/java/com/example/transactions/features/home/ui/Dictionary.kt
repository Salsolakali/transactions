package com.example.transactions.features.home.ui

import com.example.transactions.core.extensions.toCurrency
import com.example.transactions.features.home.domain.model.Rate
import com.example.transactions.features.home.domain.model.Strategy
import com.example.transactions.features.home.domain.model.Transaction
import java.math.BigDecimal
import java.math.RoundingMode

class Dictionary() {

    enum class Currency { USD, EUR, AUD, CAD }

    var conversionTableStrategy: HashMap<Strategy, Double> = HashMap()
    var allCurrencies: ArrayList<Dictionary.Currency> = ArrayList()
    var currencyTailed: ArrayList<Dictionary.Currency> = ArrayList()

    fun setUpDictionary(conversions: List<Rate>) {
        conversions.forEach {
            conversionTableStrategy[Strategy(
                toCurrency(it.from),
                toCurrency(it.to)
            )] = it.rate
        }
    }

    fun initTails() {
        allCurrencies.clear()
        currencyTailed.clear()
        allCurrencies.addAll(Currency.values())
        allCurrencies.remove(Currency.EUR)
        currencyTailed.add(Currency.EUR)
        findLonguestTail(Currency.EUR)
    }

    fun findLonguestTail(currency: Currency) {
        conversionTableStrategy.forEach {
            if (it.key.to == currency && hasNotFinishTailing()) {
                if (!currencyTailed.contains(it.key.from)) {
                    allCurrencies.remove(it.key.from)
                    currencyTailed.add(it.key.from)
                    findLonguestTail(it.key.from)
                    return
                } else if (allCurrencies.size == 1) {
                    currencyTailed.addAll(allCurrencies)
                    allCurrencies.clear()
                    return
                }
            }
        }
    }

    private fun hasNotFinishTailing(): Boolean {
        return allCurrencies.size > 0 && currencyTailed.size < 4
    }

    fun convertToEur(transactions: List<Transaction>): List<Transaction> {
        transactions.forEach {
            applyStrategy(it)
        }
        return transactions
    }

    fun applyStrategy(item: Transaction): Transaction {
        var itemConverted = item
        for (i in currencyTailed.lastIndex downTo (1)) {
            if (currencyTailed[i].toString() == itemConverted.currency) {
                itemConverted.sku = item.sku
                itemConverted.amount = conversionTableStrategy[Strategy(
                    toCurrency(itemConverted.currency),
                    currencyTailed[i - 1]
                )]?.times(
                    itemConverted.amount
                ) ?: 0.0
                itemConverted.currency = currencyTailed[i - 1].toString()
            }
        }
        itemConverted.amount =
            BigDecimal(itemConverted.amount).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        return itemConverted
    }
}