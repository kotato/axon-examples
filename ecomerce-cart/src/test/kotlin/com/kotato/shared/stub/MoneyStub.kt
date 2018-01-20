package com.kotato.shared.stub

import org.javamoney.moneta.Money
import java.math.BigDecimal

class MoneyStub {
    companion object {
        fun random(price: BigDecimal = BigDecimalStub.random(),
                   currency: String = "EUR") = Money.of(price, currency)
    }
}