package com.kotato.shared.money

import com.kotato.shared.valueobject.ValueObject
import java.math.BigDecimal

@Suppress("DataClassPrivateConstructor")
@ValueObject
data class Money private constructor(var amount: BigDecimal, var currency: String) {

    companion object {
        fun of(amount: BigDecimal, currency: String) = Money(amount.setScale(2, BigDecimal.ROUND_HALF_EVEN), currency)
        fun eur(amount: BigDecimal) = Money.of(amount, "EUR")
    }

    operator fun plus(other: Money) = Money.of(amount + other.amount, currency)

}