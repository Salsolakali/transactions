package com.example.transactions.features.home.response

import com.example.transactions.features.home.domain.model.Rate


data class RateApi(
    val from: String = "",
    val rate: String = "",
    val to: String = ""
) {
    fun toDomain(): Rate {
        return Rate(
            from = from,
            to = to,
            rate = rate.toDouble()
        )
    }
}
