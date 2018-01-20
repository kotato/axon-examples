package com.kotato.shared.money

import com.kotato.shared.valueobject.ValueObject
import java.math.BigDecimal

@Suppress("DataClassPrivateConstructor")
@ValueObject
data class Money private constructor(var amount: BigDecimal, var currency: String) {

    companion object {
        fun of(amount: BigDecimal, currency: String) = Money(amount, currency)
        fun eur(amount: BigDecimal) = Money(amount, "EUR")
    }

}